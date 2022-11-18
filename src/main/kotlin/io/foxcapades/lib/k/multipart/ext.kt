package io.foxcapades.lib.k.multipart

import java.net.http.HttpRequest

/**
 * # HTTP Request Builder POST Extension.
 *
 * Prepares the target [HttpRequest.Builder] for making a `multipart/form-data`
 * request by setting the appropriate header and [HttpRequest.BodyPublisher].
 *
 * @receiver HTTP request builder to update.
 *
 * @param body `MultiPartBody` instance to POST as the body of the outgoing
 * request.
 *
 * @return The updated `HttpRequest.Builder` instance.
 *
 * @since 1.2.0
 */
fun HttpRequest.Builder.POST(body: MultiPartBody): HttpRequest.Builder =
  header("Content-Type", body.getContentTypeHeader())
    .POST(body.makePublisher())

/**
 * # HTTP Request Builder PATCH Extension.
 *
 * Prepares the target [HttpRequest.Builder] for making a `multipart/form-data`
 * request by setting the appropriate header and [HttpRequest.BodyPublisher].
 *
 * @receiver HTTP request builder to update.
 *
 * @param body `MultiPartBody` instance to PATCH as the body of the outgoing
 * request.
 *
 * @return The updated `HttpRequest.Builder` instance.
 *
 * @since 1.2.0
 */
fun HttpRequest.Builder.PATCH(body: MultiPartBody): HttpRequest.Builder =
  header("Content-Type", body.getContentTypeHeader())
    .method("PATCH", body.makePublisher());