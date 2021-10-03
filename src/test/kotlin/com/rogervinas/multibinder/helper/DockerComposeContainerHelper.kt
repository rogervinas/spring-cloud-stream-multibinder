package com.rogervinas.multibinder.helper

import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import java.io.File

private const val KAFKA = "kafka"
private const val KAFKA_PORT = 9094
private const val ZOOKEEPER = "zookeeper"
private const val ZOOKEEPER_PORT = 2181

class DockerComposeContainerHelper {

  fun createContainer(): DockerComposeContainer<Nothing> {
    return DockerComposeContainer<Nothing>(File("docker-compose.yml"))
      .apply { withLocalCompose(true) }
      .apply {
        withExposedService(
          KAFKA,
          KAFKA_PORT,
          WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
            .apply { withStrategy(Wait.forListeningPort()) }
            .apply { withStrategy(Wait.forLogMessage(".*creating topics.*", 1)) }
        )
      }
      .apply {
        withExposedService(
          ZOOKEEPER,
          ZOOKEEPER_PORT,
          WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY)
            .apply { withStrategy(Wait.forListeningPort()) }
            .apply { withStrategy(Wait.forLogMessage(".*binding to port.*", 1)) }
        )
      }
  }
}
