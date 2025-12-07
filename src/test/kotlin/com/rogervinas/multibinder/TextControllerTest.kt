package com.rogervinas.multibinder

import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [TextController::class])
@AutoConfigureWebTestClient
class TextControllerTest {
  @Autowired
  lateinit var webClient: WebTestClient

  @MockitoBean
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
