package example

import io.foxcapades.lib.k.multipart.MultiPart
import io.foxcapades.lib.k.multipart.POST
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main(args: Array<String>) {
  val client = HttpClient.newHttpClient()

  val body = MultiPart.createBody {
    withPart {
      fieldName = "foo"
      withBody("bar")
    }

    withPart {
      fieldName = "fizz"
      withBody(File("./settings.gradle.kts"))
    }
  }

  client.send(
    HttpRequest.newBuilder(URI(args[0]))
      .POST(body)
      .build(),
    HttpResponse.BodyHandlers.discarding()
  )
}