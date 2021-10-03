package com.rogervinas.multibinder

import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [TextController::class])
class TextControllerTest {

  @Autowired
  lateinit var webClient: WebTestClient

  @MockBean
  lateinit var textProducer: TextProducer

  @Test
  fun `should produce text events`() {
    val text = "Some awesome text"
    webClient.post()
      .uri("/text")
      .bodyValue(text)
      .exchange()
      .expectStatus().isOk

    verify(textProducer).produce(TextEvent(text))
  }
}
