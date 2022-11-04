package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.PartSpecInputStream
import java.io.File

internal data class PartSpecImplFile(
  override val fieldName: String,
  override val fileName: String?,
  override val headers: Map<String, List<String>>,
  override val body: File,
) : PartSpec<File> {
  override fun toInputStream() = PartSpecInputStream(fieldName, fileName, headers, body.inputStream().buffered())
  override fun toString() = "PartSpec { name=\"$fieldName\" }"
}
