package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.PartSpecInputStream

internal class PartSpecImplByteArray(
  override val fieldName: String,
  override val fileName: String?,
  override val headers: Map<String, List<String>>,
  override val body: ByteArray,
) : PartSpec<ByteArray> {
  override fun toInputStream() =
    PartSpecInputStream(fieldName, fileName, headers, body.inputStream())

  override fun toString() = "PartSpec { name=\"$fieldName\" }"
}
