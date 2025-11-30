package com.rogervinas.multibinder

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import java.io.StringReader

@JsonTest
class TextEventSerializationTest {

  @Autowired
  lateinit var jacksonTester: JacksonTester<TextEvent>

  @Test
  fun `should serialize TextEvent`() {
    val event = TextEvent(text = "Hello World!")

    val eventJson = jacksonTester.write(event)

    assertThat(eventJson).isStrictlyEqualToJson("""{"text": "Hello World!"}""")
  }

  @Test
  fun `should deserialize TextEvent`() {
    val eventJson = """{"text": "Hello World!"}"""

    val event = jacksonTester.read(StringReader(eventJson))

    assertThat(event.`object`).isEqualTo(TextEvent(text = "Hello World!"))
  }
}
