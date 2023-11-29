package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream

class TextLengthProcessor : (KStream<String, TextEvent>) -> KStream<String, LengthEvent> {

  override fun invoke(input: KStream<String, TextEvent>): KStream<String, LengthEvent> {
    return input.mapValues { event -> LengthEvent(event.text.length) }
  }
}
