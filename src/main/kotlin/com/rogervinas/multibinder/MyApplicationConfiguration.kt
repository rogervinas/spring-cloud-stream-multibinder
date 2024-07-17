package com.rogervinas.multibinder

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.streams.kstream.KStream
import org.springframework.cloud.function.json.JacksonMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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

  // Workaround for https://github.com/spring-cloud/spring-cloud-function/issues/1158
  // Introduced in spring-cloud-function 4.1.3 via spring-cloud-dependencies 2023.0.3
  @Bean @Primary
  fun jacksonMapper(objectMapper: ObjectMapper) = JacksonMapper(objectMapper)
}
