package io.foxcapades.lib.k.multipart.util

import java.io.InputStream

private enum class HMISState {
  None,
  Key,
  KeySep,
  Value,
  ValueSep,
  LineSep,
}

internal class HeaderMapInputStream : InputStream {
  private var headers: Iterator<Map.Entry<String, List<String>>>

  private var currentHeader: Map.Entry<String, List<String>>?

  private var currentBytes: ByteArray?

  private var state: HMISState

  private var cPos: Int

  private var lPos: Int

  constructor(headers: Map<String, List<String>>) {
    this.headers = headers.iterator()
    this.currentHeader = null
    this.currentBytes = null
    this.state = HMISState.None
    this.cPos = 0
    this.lPos = 0
  }

  override fun read(): Int {
    return when (state) {
      HMISState.None     -> readNone()
      HMISState.Key      -> readKey()
      HMISState.KeySep   -> readKeySep()
      HMISState.Value    -> readValue()
      HMISState.ValueSep -> readValueSep()
      HMISState.LineSep  -> readLineSep()
    }
  }

  private fun readNone(): Int {
    if (headers.hasNext()) {
      currentHeader = headers.next()
      currentBytes = currentHeader!!.key.toByteArray()
      cPos = 0
      lPos = 0
      state = HMISState.Key
      return readKey()
    }

    return -1
  }

  private fun readKey(): Int {
    val c = currentBytes!![cPos++]
    if (cPos >= currentBytes!!.size) {
      cPos = 0
      state = HMISState.KeySep
    }
    return c.toUByte().toInt()
  }

  private fun readKeySep(): Int {
    if (cPos == 0) {
      cPos = 1
      return 58 // ':'
    }

    cPos = 0
    lPos = 0
    if (currentHeader!!.value.isEmpty()) {
      state = HMISState.LineSep
    } else {
      currentBytes = currentHeader!!.value[0].toByteArray()
      state = HMISState.Value
    }

    return 32 // ' '
  }

  private fun readValue(): Int {
    val c = currentBytes!![cPos++]

    if (cPos >= currentBytes!!.size) {
      cPos = 0
      lPos++
      state = if (lPos < currentHeader!!.value.size) {
        HMISState.ValueSep
      } else {
        HMISState.LineSep
      }
    }

    return c.toUByte().toInt()
  }

  private fun readValueSep(): Int {
    if (cPos == 0) {
      cPos = 1
      return 59 // ';'
    }

    cPos = 0
    state = HMISState.Value
    currentBytes = currentHeader!!.value[lPos].toByteArray()

    return 32 // ' '
  }

  private fun readLineSep(): Int {
    if (cPos == 0) {
      cPos = 1
      return 13 // '\r'
    }

    cPos = 0
    state = HMISState.None

    return 10 // '\n'
  }
}