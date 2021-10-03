package com.rogervinas.multibinder

import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TextController(private val textProducer: TextProducer) {

  @PostMapping("/text", consumes = [TEXT_PLAIN_VALUE])
  fun text(@RequestBody text: String) {
    textProducer.produce(TextEvent(text))
  }
}
