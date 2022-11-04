package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.PartSpecInputStream
import io.foxcapades.lib.k.multipart.util.ReaderInputStream
import java.io.Reader

internal class PartSpecImplReader(
  override val fieldName: String,
  override val fileName: String?,
  override val headers: Map<String, List<String>>,
  override val body: Reader,
) : PartSpec<Reader> {
  override fun toInputStream() = PartSpecInputStream(fieldName, fileName, headers, ReaderInputStream(body.buffered()))
  override fun toString() = "PartSpec { name=\"$fieldName\" }"
}