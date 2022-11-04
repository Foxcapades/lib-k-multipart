package io.foxcapades.lib.k.multipart.util

import io.foxcapades.lib.k.multipart.PartSpec
import java.io.InputStream

private enum class MPSState {
  None,
  InBoundaryDash,
  InBoundaryString,
  InBoundaryCRLF,
  InPart,
  InPartCRLF,
  InEndDash1,
  InEndBoundary,
  InEndDash2,
  Done,
}

private val Dash2 = byteArrayOf(45, 45)
private val CRLF  = byteArrayOf(13, 10)

internal class MultiPartStream : InputStream {
  private var state: MPSState
  private val bound: ByteArray
  private val parts: List<PartSpec<*>>
  private var stream: InputStream?
  private var pos: Int
  private var part: Int

  constructor(bound: String, parts: List<PartSpec<*>>) {
    this.state = MPSState.None
    this.bound = bound.toByteArray()
    this.parts = parts
    this.stream = null
    this.pos = 0
    this.part = 0
  }

  override fun read(): Int {
    return when (state) {
      MPSState.InPart           -> readPart()
      MPSState.Done             -> -1
      MPSState.None             -> readNone()
      MPSState.InBoundaryDash   -> readBoundDash()
      MPSState.InBoundaryString -> readBoundString()
      MPSState.InBoundaryCRLF   -> readBoundCRLF()
      MPSState.InPartCRLF       -> readPartCRLF()
      MPSState.InEndDash1       -> readEndDash1()
      MPSState.InEndBoundary    -> readEndBoundary()
      MPSState.InEndDash2       -> readEndDash2()
    }
  }

  override fun close() {
    stream?.close()
    parts.forEach { if (it.body is AutoCloseable) (it.body as AutoCloseable).close() }
  }

  private fun readNone(): Int {
    pos = 0

    if (part < parts.size) {
      stream = parts[part].toInputStream()

      state = MPSState.InBoundaryDash

      return readBoundDash()
    }

    state = MPSState.InEndDash1
    return readEndDash1()
  }

  private fun readBoundDash() = readTilNext(Dash2, MPSState.InBoundaryString)

  private fun readBoundString() = readTilNext(bound, MPSState.InBoundaryCRLF)

  private fun readBoundCRLF() = readTilNext(CRLF, MPSState.InPart)

  private fun readPart(): Int {
    val c = stream!!.read()

    if (c > -1)
      return c

    stream!!.close()
    part++
    pos = 0
    state = MPSState.InPartCRLF

    return readPartCRLF()
  }

  private fun readPartCRLF() = readTilNext(CRLF, MPSState.None)

  private fun readEndDash1() = readTilNext(Dash2, MPSState.InEndBoundary)

  private fun readEndBoundary() = readTilNext(bound, MPSState.InEndDash2)

  private fun readEndDash2() = readTilNext(Dash2, MPSState.Done)

  private fun readTilNext(bytes: ByteArray, next: MPSState): Int {
    val c = bytes[pos++]
    if (pos >= bytes.size) {
      pos = 0
      state = next
    }
    return c.toInt()
  }
}