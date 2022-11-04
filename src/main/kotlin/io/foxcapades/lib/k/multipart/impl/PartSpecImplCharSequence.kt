package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.CharSequenceReader
import io.foxcapades.lib.k.multipart.util.PartSpecInputStream
import io.foxcapades.lib.k.multipart.util.ReaderInputStream

internal data class PartSpecImplCharSequence(
  override val fieldName: String,
  override val fileName: String?,
  override val headers: Map<String, List<String>>,
  override val body: CharSequence,
) : PartSpec<CharSequence> {
  override fun toInputStream() =
    PartSpecInputStream(fieldName, fileName, headers, ReaderInputStream(CharSequenceReader(body)))
  override fun toString() = "PartSpec { name=\"$fieldName\" }"
}

