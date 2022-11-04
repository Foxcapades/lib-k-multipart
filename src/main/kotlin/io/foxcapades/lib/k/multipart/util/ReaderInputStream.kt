package io.foxcapades.lib.k.multipart.util

import java.io.InputStream
import java.io.Reader

@OptIn(ExperimentalUnsignedTypes::class)
internal class ReaderInputStream(
  private val reader: Reader,
  private val chunkSize: Int = 256
) : InputStream() {
  private val readBuf = CharArray(chunkSize)
  private var readBufSize = 0
  private var readBufPos = 0
  private val charBuf = UByteArray(2)
  private var charBufSize = 0
  private var charBufPos = 0

  init {
    refillChunk()
  }

  override fun read(): Int {
    if (charBufPos < charBufSize)
      return charBuf[charBufPos++].toInt()

    if (readBufPos < readBufSize) {
      readChar(readBuf[readBufPos++])
      return charBuf[charBufPos++].toInt()
    }

    if (readBufSize == 0)
      return -1

    refillChunk()
    return read()
  }

  private fun refillChunk() {
    val red = reader.read(readBuf)

    if (red == -1) {
      readBufSize = 0
      readBufPos = 0
    }

    else {
      readBufSize = red
      readBufPos  = 0
    }
  }

  private fun readChar(c: Char) {
    if (c.code in 1 .. 254) {
      charBuf[0] = c.code.toUByte()
      charBufSize = 1
    } else {
      val one = c.code shr 8
      val two = c.code and 255

      charBuf[0] = one.toUByte()
      charBuf[1] = two.toUByte()
      charBufSize = 2
    }

    charBufPos = 0
  }
}