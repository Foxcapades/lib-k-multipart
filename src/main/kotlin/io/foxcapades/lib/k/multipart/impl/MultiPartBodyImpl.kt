package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.MultiPartBody
import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.util.MultiPartStream
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpRequest.BodyPublishers

internal class MultiPartBodyImpl(
  override val boundary: String,
  override val parts: List<PartSpec<*>>
) : MultiPartBody {
  override fun getContentTypeHeader() = "multipart/form-data; boundary=\"$boundary\""

  override fun makePublisher(): BodyPublisher = BodyPublishers.ofInputStream { MultiPartStream(boundary, parts) }
}