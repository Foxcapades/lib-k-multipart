package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.PartSpecInputStream
import java.io.InputStream

internal class PartSpecImplInputStream(
  override val fieldName: String,
  override val fileName: String?,
  override val headers: Map<String, List<String>>,
  override val body: InputStream,
) : PartSpec<InputStream> {
  override fun toInputStream() = PartSpecInputStream(fieldName, fileName, headers, body.buffered())

  override fun toString() = "PartSpec { name=\"$fieldName\" }"
}