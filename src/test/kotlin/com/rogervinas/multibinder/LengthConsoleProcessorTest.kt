package com.rogervinas.multibinder

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension

@ExtendWith(OutputCaptureExtension::class)
internal class LengthConsoleProcessorTest {

  @Test
  fun `should log consumed length event to console`(capturedOutput: CapturedOutput) {
    val lengthConsoleProcessor = LengthConsoleProcessor()

    lengthConsoleProcessor.process(LengthEvent(53))

    assertThat(capturedOutput.out).contains("Consumed length [53]")
  }
}
