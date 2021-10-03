package com.rogervinas.multibinder

import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST
import java.util.function.Supplier

data class TextEvent(val text: String)

interface TextProducer {

  fun produce(event: TextEvent)
}

class TextStreamProducer : Supplier<Flux<TextEvent>>, TextProducer {

  private val sink = Sinks.many().unicast().onBackpressureBuffer<TextEvent>()

  override fun produce(event: TextEvent) {
    sink.emitNext(event, FAIL_FAST)
  }

  override fun get() = sink.asFlux()
}
