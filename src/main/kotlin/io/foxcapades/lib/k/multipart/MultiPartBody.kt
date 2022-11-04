package io.foxcapades.lib.k.multipart

import java.net.http.HttpRequest.BodyPublisher

/**
 * # MultiPart Body Abstraction
 *
 * Wraps the contents of a `multipart/form-data` request body and provides
 * methods to use the request body with the Java HttpClient API.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 1.0.0
 */
interface MultiPartBody {

  /**
   * Boundary String
   */
  val boundary: String

  /**
   * Parts in this multipart body.
   */
  val parts: List<PartSpec<*>>

  /**
   * Get the content type header for this body.
   *
   * Used to fill the content-type header on the outgoing request.
   *
   * Example
   * ```
   * HttpRequest.newBuilder(...)
   *   .header("Content-Type", body.getContentTypeHeader())
   *   .POST(body.makePublisher())
   *   .build()
   * ```
   */
  fun getContentTypeHeader(): String

  /**
   * Returns a new [BodyPublisher] for this multipart body.
   */
  fun makePublisher(): BodyPublisher
}