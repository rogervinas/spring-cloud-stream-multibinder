package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream
import java.util.function.Function

class TextLengthProcessor : Function<KStream<String, TextEvent>, KStream<String, LengthEvent>> {

  override fun apply(input: KStream<String, TextEvent>): KStream<String, LengthEvent> {
    return input
      .mapValues { event -> LengthEvent(event.text.length) }
  }
}
