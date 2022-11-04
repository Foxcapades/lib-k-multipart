package io.foxcapades.lib.k.multipart

import java.io.InputStream

/**
 * Part Specification
 *
 * Represents a single part in a `multipart/form-data` body.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 1.0.0
 */
interface PartSpec<T> {

  /**
   * Name of the field this part represents.
   *
   * This value is required.
   */
  val fieldName: String

  /**
   * Filename for the field this part represents.
   *
   * This value is optional.
   */
  val fileName: String?

  /**
   * Additional headers for this part.
   */
  val headers: Map<String, List<String>>

  /**
   * Raw body value for this part.
   */
  val body: T

  /**
   * Returns an [InputStream] over this [PartSpec] value.
   */
  fun toInputStream(): InputStream
}


