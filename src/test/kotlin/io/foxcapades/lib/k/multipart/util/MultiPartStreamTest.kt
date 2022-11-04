package io.foxcapades.lib.k.multipart.util

import io.foxcapades.lib.k.multipart.impl.PartSpecImplCharSequence
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MultiPartStream")
internal class MultiPartStreamTest {

  @Test
  @DisplayName("end-2-end 1")
  fun t1() {
    val out = MultiPartStream("waka waka waka", listOf(
      PartSpecImplCharSequence("taco", null, mapOf(), "smell"),
      PartSpecImplCharSequence("hello", "cruel", mapOf(), "world")
    )).reader().readText()

    val exp = "--waka waka waka\r\n" +
      "Content-Disposition: form-data; name=\"taco\"\r\n" +
      "\r\n" +
      "smell\r\n" +
      "--waka waka waka\r\n" +
      "Content-Disposition: form-data; name=\"hello\"; filename=\"cruel\"\r\n" +
      "\r\n" +
      "world\r\n" +
      "--waka waka waka--"

    assertEquals(exp, out)
  }

  @Test
  @DisplayName("end-2-end 2")
  fun t2() {
    val out = MultiPartStream("foobar", listOf()).reader().readText()
    val exp = "--foobar--"

    assertEquals(exp, out)
  }
}