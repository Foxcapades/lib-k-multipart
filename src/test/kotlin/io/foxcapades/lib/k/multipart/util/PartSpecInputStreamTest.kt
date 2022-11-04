package io.foxcapades.lib.k.multipart.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PartSpecInputStream")
internal class PartSpecInputStreamTest {

  @Test
  @DisplayName("end-2-end 1")
  fun t1() {
    val fieldName = "my-field"
    val fileName  = "my-file"
    val headers   = mapOf("Content-Type" to listOf("application/json"))
    val body      = """
      {
        "some": "json",
        "content": [
          "to",
          "send"
        ]
      }
    """.trimIndent()

    val expect = "Content-Disposition: form-data; name=\"my-field\"; filename=\"my-file\"\r\n" +
      "Content-Type: application/json\r\n" +
      "\r\n" +
      body +
      "\r\n"

    val stream = PartSpecInputStream(fieldName, fileName, headers, body.byteInputStream())

    assertEquals(expect, stream.reader().readText())
  }

  @Test
  @DisplayName("end-2-end 2")
  fun t2() {
    val fieldName = "my-field"
    val body      = """
      {
        "some": "json",
        "content": [
          "to",
          "send"
        ]
      }
    """.trimIndent()

    val expect = "Content-Disposition: form-data; name=\"my-field\"\r\n" +
      "\r\n" +
      body +
      "\r\n"

    val stream = PartSpecInputStream(fieldName, null, mapOf(), body.byteInputStream())

    assertEquals(expect, stream.reader().readText())
  }

}