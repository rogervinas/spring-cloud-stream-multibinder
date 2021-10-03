package com.rogervinas.multibinder

import org.apache.kafka.streams.kstream.KStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Function

@Configuration
class MyApplicationConfiguration {

  @Bean
  fun textProducer() = TextStreamProducer()

  @Bean
  fun textLengthProcessor(): Function<KStream<String, TextEvent>, KStream<String, LengthEvent>> = TextLengthProcessor()

  @Bean
  fun lengthConsumer(lengthProcessor: LengthProcessor) = LengthStreamConsumer(lengthProcessor)

  @Bean
  fun lengthProcessor() = LengthConsoleProcessor()
}

