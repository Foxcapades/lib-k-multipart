package io.foxcapades.lib.k.multipart

import java.net.http.HttpRequest.BodyPublisher

/**
 * # MultiPart Body Builder
 *
 * Builder used to construct a `multipart/form-data` body.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 1.0.0
 */
interface MultiPartBuilder {

  /**
   * Builds and appends a new [PartSpec] to this builder.
   */
  fun withPart(fn: PartSpecBuilder.() -> Unit)

  /**
   * Builds and appends the given [PartSpecBuilder] to this builder.
   */
  fun withPart(builder: PartSpecBuilder)

  /**
   * Appends the given [PartSpec] to this builder.
   */
  fun withPart(part: PartSpec<*>)

  /**
   * Builds a [BodyPublisher] instance from the current state of this
   * `multipart/form-data` builder.
   */
  fun build(): MultiPartBody
}