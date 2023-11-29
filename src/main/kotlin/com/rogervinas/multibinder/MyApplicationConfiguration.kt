package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier

@Configuration
class MyApplicationConfiguration {

  @Bean
  fun textStreamProducer() = TextStreamProducer()

  @Bean
  fun textProducer(textStreamProducer: TextStreamProducer): Supplier<Flux<TextEvent>> = textStreamProducer

  @Bean
  fun textLengthProcessor(): Function<KStream<String, TextEvent>, KStream<String, LengthEvent>> = TextLengthProcessor()

  @Bean
  fun lengthConsumer(lengthProcessor: LengthProcessor): Consumer<LengthEvent> = LengthStreamConsumer(lengthProcessor)

  @Bean
  fun lengthProcessor() = LengthConsoleProcessor()
}
