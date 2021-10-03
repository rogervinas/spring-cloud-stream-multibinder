package com.rogervinas.multibinder

import com.rogervinas.multibinder.helper.DockerComposeContainerHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.awaitility.Awaitility.await
import org.testcontainers.shaded.org.awaitility.Durations.ONE_MINUTE

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ExtendWith(OutputCaptureExtension::class)
internal class MyApplicationIntegrationTest {

  companion object {

    @Container
    val container = DockerComposeContainerHelper().createContainer()
  }

  @Value("http://localhost:\${local.server.port}")
  lateinit var localUrl: String

  @Autowired
  lateinit var webClient: WebTestClient

  @Test
  fun `should process text lengths`(capturedOutput: CapturedOutput) {
    postText("Do")
    postText("or do not")
    postText("there is no try")

    await().atMost(ONE_MINUTE).untilAsserted {
      assertThat(capturedOutput.out).contains("Consumed length [2]")
      assertThat(capturedOutput.out).contains("Consumed length [9]")
      assertThat(capturedOutput.out).contains("Consumed length [15]")
    }
  }

  private fun postText(text: String) {
    webClient.post()
      .uri("/text")
      .bodyValue(text)
      .exchange()
      .expectStatus().isOk
  }
}
