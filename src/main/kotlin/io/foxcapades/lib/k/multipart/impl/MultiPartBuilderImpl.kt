package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.MultiPartBuilder
import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.PartSpecBuilder

internal class MultiPartBuilderImpl() : MultiPartBuilder {
  private val boundary = "kmp-" + System.currentTimeMillis().toString(16)
  private val parts = ArrayList<PartSpec<*>>(1)

  override fun withPart(fn: PartSpecBuilder.() -> Unit) {
    parts.add(PartSpecBuilderImpl().apply(fn).build())
  }

  override fun withPart(builder: PartSpecBuilder) {
    parts.add(builder.build())
  }

  override fun withPart(part: PartSpec<*>) {
    parts.add(part)
  }

  override fun build() = MultiPartBodyImpl(boundary, parts)
}