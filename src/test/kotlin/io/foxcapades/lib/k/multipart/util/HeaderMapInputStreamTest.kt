package io.foxcapades.lib.k.multipart.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("HeaderMapInputStream")
internal class HeaderMapInputStreamTest {

  @Test
  fun t1() {
    val input = mapOf(
      "Hello" to listOf("Goodbye"),
      "Cruel" to listOf("World"),
      "Puppy" to listOf("Lasagna", "Puppy", "Stink"),
      "Empty" to listOf(),
    )

    val expect = "Hello: Goodbye\r\n" +
      "Cruel: World\r\n" +
      "Puppy: Lasagna; Puppy; Stink\r\n" +
      "Empty: \r\n"

    assertEquals(expect, HeaderMapInputStream(input).reader().readText())
  }
}