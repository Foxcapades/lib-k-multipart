package io.foxcapades.lib.k.multipart.impl

import io.foxcapades.lib.k.multipart.HeaderMapBuilder
import io.foxcapades.lib.k.multipart.PartSpec
import io.foxcapades.lib.k.multipart.PartSpecBuilder
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.nio.file.Files

internal class PartSpecBuilderImpl : PartSpecBuilder {
  override var fieldName: String? = null
  override var fileName: String? = null
  override val headers: HeaderMapBuilder = HeaderMapBuilderImpl()
  override var body: Any? = null
    private set

  private inline fun alsoCT(ct: String, fn: () -> Unit): PartSpecBuilder {
    fn()
    contentType(ct)
    return this
  }

  override fun fieldName(name: String)   = apply { fieldName = name }
  override fun fileName(name: String)    = apply { fileName = name }
  override fun contentType(type: String) = apply { headers["Content-Type"] = type }

  override fun withBody(body: InputStream)  = also { it.body = body }
  override fun withBody(body: Reader)       = also { it.body = body }
  override fun withBody(body: CharSequence) = also { it.body = body }
  override fun withBody(body: ByteArray)    = also { it.body = body }
  override fun withBody(body: File, detectContentType: Boolean): PartSpecBuilder {
    this.body = body
    this.fileName = body.name
    return if (detectContentType)
      contentType(Files.probeContentType(body.toPath()))
    else
      this
  }

  override fun withBody(body: InputStream, contentType: String)  = alsoCT(contentType) { this.body = body }
  override fun withBody(body: Reader, contentType: String)       = alsoCT(contentType) { this.body = body }
  override fun withBody(body: CharSequence, contentType: String) = alsoCT(contentType) { this.body = body }
  override fun withBody(body: ByteArray, contentType: String)    = alsoCT(contentType) { this.body = body }
  override fun withBody(body: File, contentType: String)         = alsoCT(contentType) {
    this.body = body
    this.fileName = body.name
  }

  override fun withJSONBody(body: InputStream)  = alsoCT("application/json") { this.body = body }
  override fun withJSONBody(body: Reader)       = alsoCT("application/json") { this.body = body }
  override fun withJSONBody(body: CharSequence) = alsoCT("application/json") { this.body = body }
  override fun withJSONBody(body: ByteArray)    = alsoCT("application/json") { this.body = body }
  override fun withJSONBody(body: File)         = alsoCT("application/json") {
    this.body = body
    this.fileName = body.name
  }

  override fun withXMLBody(body: InputStream)  = alsoCT("text/xml") { this.body = body }
  override fun withXMLBody(body: Reader)       = alsoCT("text/xml") { this.body = body }
  override fun withXMLBody(body: CharSequence) = alsoCT("text/xml") { this.body = body }
  override fun withXMLBody(body: ByteArray)    = alsoCT("text/xml") { this.body = body }
  override fun withXMLBody(body: File)         = alsoCT("text/xml") {
    this.body = body
    this.fileName = body.name
  }

  override fun withPlainTextBody(body: InputStream)  = alsoCT("text/plain") { this.body = body }
  override fun withPlainTextBody(body: Reader)       = alsoCT("text/plain") { this.body = body }
  override fun withPlainTextBody(body: CharSequence) = alsoCT("text/plain") { this.body = body }
  override fun withPlainTextBody(body: ByteArray)    = alsoCT("text/plain") { this.body = body }
  override fun withPlainTextBody(body: File)         = alsoCT("text/plain") {
    this.body = body
    this.fileName = body.name
  }

  override fun withOctetStreamBody(body: InputStream)  = alsoCT("application/octet-stream") { this.body = body }
  override fun withOctetStreamBody(body: Reader)       = alsoCT("application/octet-stream") { this.body = body }
  override fun withOctetStreamBody(body: CharSequence) = alsoCT("application/octet-stream") { this.body = body }
  override fun withOctetStreamBody(body: ByteArray)    = alsoCT("application/octet-stream") { this.body = body }
  override fun withOctetStreamBody(body: File)         = alsoCT("application/octet-stream") {
    this.body = body
    this.fileName = body.name
  }

  override fun build(): PartSpec<*> {
    if (fieldName == null)
      throw IllegalStateException("Part spec field name was null.")
    if (body == null)
      throw IllegalStateException("Part spec body was null.")

    return when (body) {
      is InputStream  -> PartSpecImplInputStream(fieldName!!, fileName, headers.build(), body as InputStream)
      is CharSequence -> PartSpecImplCharSequence(fieldName!!, fileName, headers.build(), body as CharSequence)
      is Reader       -> PartSpecImplReader(fieldName!!, fileName, headers.build(), body as Reader)
      is File         -> PartSpecImplFile(fieldName!!, fileName, headers.build(), body as File)
      is ByteArray    -> PartSpecImplByteArray(fieldName!!, fileName, headers.build(), body as ByteArray)
      else            -> throw IllegalStateException()
    }
  }
}