package io.foxcapades.lib.k.multipart.util

import java.io.Reader
import java.util.*

class CharSequenceReader(private val raw: CharSequence) : Reader() {

  private var pos = 0

  override fun read(cbuf: CharArray, off: Int, len: Int): Int {
    Objects.checkFromIndexSize(off, len, cbuf.size)

    if (len == 0)
      return 0

    if (pos >= raw.length)
      return -1

    val avail = raw.length - pos

    if (avail <= 0)
      return 0

    val read = if (len > avail) avail else len

    for (i in 0 until read)
      cbuf[off + i] = raw[pos + i]

    pos += read
    return read
  }

  override fun close() {}
}