package com.rogervinas.multibinder

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TextFluxProducerTest {

  @Test
  fun `should produce text events`() {
    val producer = TextFluxProducer()

    val events = mutableListOf<TextEvent>()
    producer().subscribe(events::add)

    producer.produce(TextEvent("Well"))
    producer.produce(TextEvent("nobody is"))
    producer.produce(TextEvent("perfect!"))

    assertThat(events).containsExactly(
      TextEvent("Well"),
      TextEvent("nobody is"),
      TextEvent("perfect!")
    )
  }
}
