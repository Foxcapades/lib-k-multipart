import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.21"
  id("org.jetbrains.dokka") version "1.7.20"
  `maven-publish`
  signing
}

group = "io.foxcapades.lib"
version = "1.2.0"

repositories {
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11

  withJavadocJar()
  withSourcesJar()
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

tasks.dokkaHtml {
  outputDirectory.set(file("docs/dokka/${version}"))
}

publishing {
  repositories {
    maven {
      name = "GitHub"
      url = uri("https://maven.pkg.github.com/foxcapades/lib-k-multipart")
      credentials {
        username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
        password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
      }
    }

    maven {
      name = "Sonatype"
      url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = project.findProperty("nexus.user") as String? ?: System.getenv("NEXUS_USER")
        password = project.findProperty("nexus.pass") as String? ?: System.getenv("NEXUS_PASS")
      }
    }
  }

  publications {
    create<MavenPublication>("gpr") {
      from(components["java"])
      pom {
        name.set("Kotlin MultiPart BodyPublisher")
        description.set("Provides a BodyPublisher for the Java HttpClient API allowing easy multipart uploads.")
        url.set("https://github.com/foxcapades/lib-k-multipart")

        licenses {
          license {
            name.set("MIT")
          }
        }

        developers {
          developer {
            id.set("epharper")
            name.set("Elizabeth Paige Harper")
            email.set("foxcapades.io@gmail.com")
            url.set("https://github.com/foxcapades")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/foxcapades/lib-k-multipart.git")
          developerConnection.set("scm:git:ssh://github.com/foxcapades/lib-k-multipart.git")
          url.set("https://github.com/foxcapades/lib-k-multipart")
        }
      }
    }
  }
}

signing {
  useGpgCmd()

  sign(configurations.archives.get())
  sign(publishing.publications["gpr"])
}
