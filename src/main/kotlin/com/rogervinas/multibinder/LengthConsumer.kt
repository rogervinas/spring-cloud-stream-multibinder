package com.rogervinas.multibinder

import org.slf4j.LoggerFactory

data class LengthEvent(val length: Int)

interface LengthProcessor {
  fun process(event: LengthEvent)
}

class LengthConsoleProcessor : LengthProcessor {
  private val logger = LoggerFactory.getLogger(LengthConsoleProcessor::class.java)

  override fun process(event: LengthEvent) {
    logger.info("Consumed length [${event.length}]")
  }
}

class LengthStreamConsumer(private val processor: LengthProcessor) : (LengthEvent) -> Unit {
  override fun invoke(event: LengthEvent) {
    processor.process(event)
  }
}
