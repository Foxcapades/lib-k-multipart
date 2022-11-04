package io.foxcapades.lib.k.multipart.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("CharSequenceReader")
internal class CharSequenceReaderTest {

  @Test
  @DisplayName("end-2-end 1")
  fun t1() {
    val foo = "this is a test string that contains more than a few characters in it"

    assertEquals(foo, CharSequenceReader(foo).readText())


  }
}