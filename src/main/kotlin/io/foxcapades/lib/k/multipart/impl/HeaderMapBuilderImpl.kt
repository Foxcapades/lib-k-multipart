package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.HeaderMapBuilder

internal class HeaderMapBuilderImpl : HeaderMapBuilder {
  private val raw = HashMap<String, List<String>>()

  override fun set(key: String, value: String) {
    raw[key] = listOf(value)
  }

  override fun set(key: String, value: Iterable<String>) {
    raw[key] = value.toList()
  }

  override fun set(key: String, value: Array<String>) {
    raw[key] = value.toList()
  }

  override fun build(): Map<String, List<String>> {
    return raw
  }
}