plugins {
  kotlin("jvm") version "1.7.21"
  application
}

repositories {
  mavenCentral()
}

kotlin {
  jvmToolchain {
    this.languageVersion.set(JavaLanguageVersion.of(11))
  }
}

dependencies {
  implementation("io.foxcapades.lib:k-multipart:1.1.0")
}

application {
  mainClass.set("example.MainKt")
}