package io.foxcapades.lib.k.multipart

import io.foxcapades.lib.k.multipart.impl.MultiPartBuilderImpl
import java.net.http.HttpClient
import java.net.http.HttpRequest

/**
 * MultiPart Body Utils
 *
 * Provides methods for constructing a multipart/form-data request body that is
 * usable with the JDK [HttpClient] API.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 1.0.0
 */
object MultiPart {
  /**
   * Creates a `multipart/form-data` body publisher suitable for use with the
   * standard library [HttpClient] API.
   **
   * @param fn Builder function.
   *
   * @return [HttpRequest.BodyPublisher] wrapping the `multipart/form-data`
   * body.
   */
  fun createBody(fn: MultiPartBuilder.() -> Unit) =
    MultiPartBuilderImpl().apply(fn).build()
}
