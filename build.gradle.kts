import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
  id("org.springframework.boot") version "3.3.1"
  id("io.spring.dependency-management") version "1.1.6"
  kotlin("jvm") version "2.0.0"
  kotlin("plugin.spring") version "2.0.0"
  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "com.rogervinas"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
  mavenCentral()
}

val springCloudVersion = "2023.0.3"
val testContainersVersion = "1.19.8"

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.cloud:spring-cloud-stream")
  implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
  implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka-streams")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.apache.kafka:kafka-streams-test-utils")
  testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
  testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
  }
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events(PASSED, SKIPPED, FAILED)
    exceptionFormat = FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
  }
}
