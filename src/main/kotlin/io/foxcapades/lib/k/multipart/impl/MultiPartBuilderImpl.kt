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

  override fun withPart(builder: PartSpecBuilder): MultiPartBuilder {
    parts.add(builder.build())
    return this
  }

  override fun withPart(part: PartSpec<*>): MultiPartBuilder {
    parts.add(part)
    return this
  }

  override fun build() = MultiPartBodyImpl(boundary, parts)
}