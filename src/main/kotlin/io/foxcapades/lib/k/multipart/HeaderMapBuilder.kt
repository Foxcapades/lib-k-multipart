package io.foxcapades.lib.k.multipart

/**
 * # Body Part Header Map Builder
 *
 * Provides methods for building a map of headers.
 *
 * @author Elizabeth Paige Harper - https://github.com/foxcapades
 * @since 1.0.0
 */
interface HeaderMapBuilder {

  /**
   * Sets the target header to the given single string value.
   *
   * @param key Name of the header to set.
   *
   * @param value Value to set to the header.
   */
  operator fun set(key: String, value: String)

  /**
   * Sets the target header to the given collection of values.
   *
   * @param key Name of the header to set.
   *
   * @param value Values to set to the header.
   */
  operator fun set(key: String, value: Iterable<String>)

  /**
   * Sets the target header to the given array of values.
   *
   * @param key Name of the header to set.
   *
   * @param value Values to set to the header.
   */
  operator fun set(key: String, value: Array<String>)

  /**
   * Builds the header map.
   *
   * @return The constructed header map.
   */
  fun build(): Map<String, List<String>>
}