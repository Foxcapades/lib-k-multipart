package io.foxcapades.lib.k.multipart.util

import java.io.InputStream

private enum class PSISState {
  InContentDisposition1,
  InFieldName,
  InContentDisposition2,
  InFileName,
  InContentDisposition3,
  InHeaderStream,
  InPrefixCRLF,
  InPart,
  Done,
}

// "Content-Disposition: form-data; name=\""
private val bCD1 = byteArrayOf(67, 111, 110, 116, 101, 110, 116, 45, 68, 105,
  115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32, 102, 111, 114, 109, 45,
  100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34)

// "\"; filename=\""
private val bCD2 = byteArrayOf(34, 59, 32, 102, 105, 108, 101, 110, 97,
  109, 101, 61, 34)

// "\"\r\n"
private val bCD3 = byteArrayOf(34, 13, 10)

// "\r\n"
private val bCRLF = byteArrayOf(13, 10)


internal class PartSpecInputStream : InputStream {
  private val fieldName: ByteArray
  private val fileName: ByteArray?
  private val headers: HeaderMapInputStream?
  private val body: InputStream

  private var state = PSISState.InContentDisposition1
  private var pos = 0

  constructor(fieldName: String, fileName: String?, headers: Map<String, List<String>>, body: InputStream) {
    this.fieldName = fieldName.toByteArray()
    this.fileName  = fileName?.toByteArray()
    this.headers   = HeaderMapInputStream(headers)
    this.body      = body
  }

  override fun read(): Int {
    return when (state) {
      PSISState.InPart                -> readTilNext(body, PSISState.Done)
      PSISState.InContentDisposition1 -> readTilNext(bCD1, PSISState.InFieldName)
      PSISState.InFieldName           -> readTilNext(fieldName, if (fileName != null) PSISState.InContentDisposition2 else PSISState.InContentDisposition3)
      PSISState.InContentDisposition2 -> readTilNext(bCD2, PSISState.InFileName)
      PSISState.InFileName            -> readTilNext(fileName!!, PSISState.InContentDisposition3)
      PSISState.InContentDisposition3 -> readTilNext(bCD3, if (headers != null) PSISState.InHeaderStream else PSISState.InPrefixCRLF)
      PSISState.InHeaderStream        -> readTilNext(headers!!, PSISState.InPrefixCRLF)
      PSISState.InPrefixCRLF          -> readTilNext(bCRLF, PSISState.InPart)
      PSISState.Done                  -> -1
    }
  }

  override fun close() {
    headers?.close()
    body.close()
  }

  private fun readTilNext(bytes: InputStream, next: PSISState): Int {
    val c = bytes.read()

    if (c > -1)
      return c

    bytes.close()
    pos = 0
    state = next
    return read()
  }

  private fun readTilNext(bytes: ByteArray, next: PSISState): Int {
    val c = bytes[pos++]
    if (pos >= bytes.size) {
      pos = 0
      state = next
    }
    return c.toUByte().toInt()
  }
}