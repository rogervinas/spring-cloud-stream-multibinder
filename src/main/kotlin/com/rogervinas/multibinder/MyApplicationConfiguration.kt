package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Function

@Configuration
class MyApplicationConfiguration {
  @Bean
  fun textFluxProducer() = TextFluxProducer()

  @Bean
  fun textProducer(textProducer: TextFluxProducer): () -> Flux<TextEvent> = textProducer

  @Bean
  fun textLengthProcessor(): Function<KStream<String, TextEvent>, KStream<String, LengthEvent>> = TextLengthProcessor()

  @Bean
  fun lengthConsumer(lengthProcessor: LengthProcessor): (LengthEvent) -> Unit = LengthStreamConsumer(lengthProcessor)

  @Bean
  fun lengthConsoleProcessor() = LengthConsoleProcessor()
}
