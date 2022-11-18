package example;

import io.foxcapades.lib.k.multipart.MultiPart;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

  public static void main(String[] args) throws Exception {
    var client = HttpClient.newHttpClient();

    var body = MultiPart.builder()
      .withPart(MultiPart.partSpec()
        .fieldName("foo")
        .withBody("bar"))
      .withPart(MultiPart.partSpec()
        .fieldName("fizz")
        .withBody(new File("./settings.gradle.kts"), true))
      .build();

    client.send(
      HttpRequest.newBuilder()
        .uri(URI.create(args[0]))
        .header("Content-Type", body.getContentTypeHeader())
        .POST(body.makePublisher())
        .build(),
      HttpResponse.BodyHandlers.discarding()
    );
  }
}
