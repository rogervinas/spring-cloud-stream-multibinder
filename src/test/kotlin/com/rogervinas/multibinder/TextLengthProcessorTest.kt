package com.rogervinas.multibinder

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.TestInputTopic
import org.apache.kafka.streams.TestOutputTopic
import org.apache.kafka.streams.TopologyTestDriver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer
import org.springframework.kafka.support.serializer.JacksonJsonSerde
import java.util.Properties

private const val TOPIC_IN = "topic.in"
private const val TOPIC_OUT = "topic.out"

private const val KEY1 = "key1"
private const val KEY2 = "key2"
private const val KEY3 = "key3"

internal class TextLengthProcessorTest {
  private lateinit var topologyTestDriver: TopologyTestDriver
  private lateinit var topicIn: TestInputTopic<String, TextEvent>
  private lateinit var topicOut: TestOutputTopic<String, LengthEvent>

  @BeforeEach
  fun beforeEach() {
    val stringSerde = Serdes.StringSerde()
    val textEventSerializer = JacksonJsonSerde(TextEvent::class.java).serializer()
    val lengthEventDeserializer = JacksonJsonSerde(LengthEvent::class.java).deserializer()
    val streamsBuilder = StreamsBuilder()

    TextLengthProcessor()
      .apply(streamsBuilder.stream(TOPIC_IN))
      .to(TOPIC_OUT)

    val config =
      Properties().apply {
        setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.javaClass.name)
        setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JacksonJsonSerde::class.java.name)
        setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "test")
        setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "test-server")
        setProperty(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*")
      }
    val topology = streamsBuilder.build()
    topologyTestDriver = TopologyTestDriver(topology, config)
    topicIn = topologyTestDriver.createInputTopic(TOPIC_IN, stringSerde.serializer(), textEventSerializer)
    topicOut = topologyTestDriver.createOutputTopic(TOPIC_OUT, stringSerde.deserializer(), lengthEventDeserializer)
  }

  @AfterEach
  fun afterEach() {
    topologyTestDriver.close()
  }

  @Test
  fun `should produce length events from text events`() {
    topicIn.pipeInput(KEY1, TextEvent("Hello!"))
    topicIn.pipeInput(KEY2, TextEvent("How are you?"))
    topicIn.pipeInput(KEY3, TextEvent("Bye!"))

    assertThat(topicOut.readKeyValuesToList()).containsExactly(
      KeyValue(KEY1, LengthEvent(6)),
      KeyValue(KEY2, LengthEvent(12)),
      KeyValue(KEY3, LengthEvent(4)),
    )
  }
}
