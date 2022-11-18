plugins {
  java
  application
}

repositories {
  mavenCentral()
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

dependencies {
  implementation("io.foxcapades.lib:k-multipart:1.2.0")
}

application {
  mainClass.set("example.Main")
}