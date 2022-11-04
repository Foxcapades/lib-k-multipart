package io.foxcapades.lib.k.multipart

import java.io.File
import java.io.InputStream
import java.io.Reader

/**
 * # Part Specification Builder
 *
 * Builder for constructing a [PartSpec] instance.
 *
 * @author Elizabeth Paige Harper
 * @since 1.0.0
 */
interface PartSpecBuilder {

  /**
   * Field name for the part being built.
   *
   * This value is required, and attempting to build this part without this
   * value set will result in an exception.
   */
  var fieldName: String?

  /**
   * File name for the part being built.
   *
   * This value is optional.
   */
  var fileName: String?

  /**
   * Header map builder.
   */
  val headers: HeaderMapBuilder

  /**
   * Current body value.
   */
  val body: Any?

  /**
   * Sets the field name for this part.
   *
   * @param name New field name value.
   *
   * @return This builder instance.
   */
  fun fieldName(name: String): PartSpecBuilder

  /**
   * Sets the file name for this part.
   *
   * @param name New file name value.
   *
   * @return This builder instance.
   */
  fun fileName(name: String): PartSpecBuilder

  /**
   * Sets the Content-Type header for this part.
   *
   * @param type New Content-Type value.
   *
   * @return This builder instance.
   */
  fun contentType(type: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [InputStream].
   *
   * @param body InputStream to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: InputStream): PartSpecBuilder

  /**
   * Sets the body for this part to the given [InputStream] and additionally
   * sets the Content-Type header to the given value.
   *
   * @param body InputStream to use as the body for this part.
   *
   * @param contentType Content-Type header value to set for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: InputStream, contentType: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [Reader].
   *
   * @param body Reader to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: Reader): PartSpecBuilder

  /**
   * Sets the body for this part to the given [Reader] and additionally
   * sets the Content-Type header to the given value.
   *
   * @param body Reader to use as the body for this part.
   *
   * @param contentType Content-Type header value to set for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: Reader, contentType: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [CharSequence].
   *
   * @param body String to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: CharSequence): PartSpecBuilder

  /**
   * Sets the body for this part to the given [CharSequence] and additionally
   * sets the Content-Type header to the given value.
   *
   * @param body String to use as the body for this part.
   *
   * @param contentType Content-Type header value to set for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: CharSequence, contentType: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [ByteArray].
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: ByteArray): PartSpecBuilder

  /**
   * Sets the body for this part to the given [ByteArray] and additionally
   * sets the Content-Type header to the given value.
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @param contentType Content-Type header value to set for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: ByteArray, contentType: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [File] and sets the [fileName]
   * value to the name of the given file; additionally, may optionally set the
   * Content-Type header to the detected mime-type of the file.
   *
   * @param body File to use as the body for this part.
   *
   * @param detectContentType Whether this part's Content-Type value should be
   * automatically detected from the contents of the given [body] file.
   *
   * Defaults to `true`.
   *
   * @return This builder instance.
   */
  fun withBody(body: File, detectContentType: Boolean = true): PartSpecBuilder

  /**
   * Sets the body for this part to the given [File] and additionally sets the
   * Content-Type header to the given value.
   *
   * @param body File to use as the body for this part.
   *
   * @param contentType Content-Type header value to set for this part.
   *
   * @return This builder instance.
   */
  fun withBody(body: File, contentType: String): PartSpecBuilder


  /**
   * Sets the body for this part to the given [InputStream] and additionally
   * sets the Content-Type header to `application/json`.
   *
   * @param body InputStream to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withJSONBody(body: InputStream): PartSpecBuilder

  /**
   * Sets the body for this part to the given [Reader] and additionally
   * sets the Content-Type header to `application/json`.
   *
   * @param body Reader to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withJSONBody(body: Reader): PartSpecBuilder

  /**
   * Sets the body for this part to the given [ByteArray] and additionally
   * sets the Content-Type header to `application/json`.
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withJSONBody(body: ByteArray): PartSpecBuilder

  /**
   * Sets the body for this part to the given [CharSequence] and additionally
   * sets the Content-Type header to `application/json`.
   *
   * @param body String to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withJSONBody(body: CharSequence): PartSpecBuilder

  /**
   * Sets the body for this part to the given [File], sets the [fileName] field
   * to the name of the given file, and sets the Content-Type header to
   * `application/json`.
   *
   * @param body File to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withJSONBody(body: File): PartSpecBuilder


  /**
   * Sets the body for this part to the given [InputStream] and additionally
   * sets the Content-Type header to `text/xml`.
   *
   * @param body InputStream to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withXMLBody(body: InputStream): PartSpecBuilder

  /**
   * Sets the body for this part to the given [Reader] and additionally
   * sets the Content-Type header to `text/xml`.
   *
   * @param body Reader to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withXMLBody(body: Reader): PartSpecBuilder

  /**
   * Sets the body for this part to the given [ByteArray] and additionally
   * sets the Content-Type header to `text/xml`.
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withXMLBody(body: ByteArray): PartSpecBuilder

  /**
   * Sets the body for this part to the given [CharSequence] and additionally
   * sets the Content-Type header to `text/xml`.
   *
   * @param body String to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withXMLBody(body: CharSequence): PartSpecBuilder

  /**
   * Sets the body for this part to the given [File], sets the [fileName] field
   * to the name of the given file, and sets the Content-Type header to
   * `text/xml`.
   *
   * @param body File to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withXMLBody(body: File): PartSpecBuilder


  /**
   * Sets the body for this part to the given [InputStream] and additionally
   * sets the Content-Type header to `text/plain`.
   *
   * @param body InputStream to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withPlainTextBody(body: InputStream): PartSpecBuilder

  /**
   * Sets the body for this part to the given [Reader] and additionally
   * sets the Content-Type header to `text/plain`.
   *
   * @param body Reader to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withPlainTextBody(body: Reader): PartSpecBuilder

  /**
   * Sets the body for this part to the given [ByteArray] and additionally
   * sets the Content-Type header to `text/plain`.
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withPlainTextBody(body: ByteArray): PartSpecBuilder

  /**
   * Sets the body for this part to the given [CharSequence] and additionally
   * sets the Content-Type header to `text/plain`.
   *
   * @param body String to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withPlainTextBody(body: CharSequence): PartSpecBuilder

  /**
   * Sets the body for this part to the given [File], sets the [fileName] field
   * to the name of the given file, and sets the Content-Type header to
   * `text/plain`.
   *
   * @param body File to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withPlainTextBody(body: File): PartSpecBuilder


  /**
   * Sets the body for this part to the given [InputStream] and additionally
   * sets the Content-Type header to `application/octet-stream`.
   *
   * @param body InputStream to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withOctetStreamBody(body: InputStream): PartSpecBuilder

  /**
   * Sets the body for this part to the given [Reader] and additionally
   * sets the Content-Type header to `application/octet-stream`.
   *
   * @param body Reader to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withOctetStreamBody(body: Reader): PartSpecBuilder

  /**
   * Sets the body for this part to the given [ByteArray] and additionally
   * sets the Content-Type header to `application/octet-stream`.
   *
   * @param body ByteArray to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withOctetStreamBody(body: ByteArray): PartSpecBuilder

  /**
   * Sets the body for this part to the given [CharSequence] and additionally
   * sets the Content-Type header to `application/octet-stream`.
   *
   * @param body String to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withOctetStreamBody(body: CharSequence): PartSpecBuilder

  /**
   * Sets the body for this part to the given [File], sets the [fileName] field
   * to the name of the given file, and sets the Content-Type header to
   * `application/octet-stream`.
   *
   * @param body File to use as the body for this part.
   *
   * @return This builder instance.
   */
  fun withOctetStreamBody(body: File): PartSpecBuilder

  /**
   * Builds a new PartSpec instance from the current state of this builder.
   *
   * If [fieldName] is `null`, an exception will be thrown.
   * If [body] is `null`, an exception will be thrown.
   *
   * @return The built [PartSpec] instance.
   */
  fun build(): PartSpec<*>
}