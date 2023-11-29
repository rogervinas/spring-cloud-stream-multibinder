package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@Configuration
class MyApplicationConfiguration {

  @Bean
  fun textStreamProducer() = TextStreamProducer()

  @Bean("text-producer")
  fun textProducerFunction(producer: TextStreamProducer): () -> Flux<TextEvent> = producer::invoke

  @Bean("text-length-processor")
  fun textLengthProcessorFunction(): (KStream<String, TextEvent>) -> KStream<String, LengthEvent> = TextLengthProcessor()

  @Bean("length-consumer")
  fun lengthConsumerFunction(lengthProcessor: LengthProcessor): (LengthEvent) -> Unit =
    LengthStreamConsumer(lengthProcessor)

  @Bean
  fun lengthConsoleProcessor() = LengthConsoleProcessor()
}

