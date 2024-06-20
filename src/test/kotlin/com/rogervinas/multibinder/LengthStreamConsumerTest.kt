package com.rogervinas.multibinder

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class LengthStreamConsumerTest {
  @Test
  fun `should consume length events`() {
    val lengthProcessor = mock(LengthProcessor::class.java)
    val lengthStreamConsumer = LengthStreamConsumer(lengthProcessor)

    lengthStreamConsumer(LengthEvent(10))
    lengthStreamConsumer(LengthEvent(20))
    lengthStreamConsumer(LengthEvent(30))

    verify(lengthProcessor).process(LengthEvent(10))
    verify(lengthProcessor).process(LengthEvent(20))
    verify(lengthProcessor).process(LengthEvent(30))
  }
}
