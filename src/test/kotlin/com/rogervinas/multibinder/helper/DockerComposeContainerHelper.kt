package com.rogervinas.multibinder.helper

import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait.forListeningPort
import org.testcontainers.containers.wait.strategy.Wait.forLogMessage
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import org.testcontainers.containers.wait.strategy.WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY
import java.io.File

private const val KAFKA = "kafka"
private const val KAFKA_PORT = 9094
private const val ZOOKEEPER = "zookeeper"
private const val ZOOKEEPER_PORT = 2181

class DockerComposeContainerHelper {

  fun createContainer(): ComposeContainer = ComposeContainer(File("docker-compose.yml"))
    .withLocalCompose(true)
    .withExposedService(
      KAFKA,
      KAFKA_PORT,
      WaitAllStrategy(WITH_INDIVIDUAL_TIMEOUTS_ONLY)
        .withStrategy(forListeningPort())
        .withStrategy(forLogMessage(".*creating topics.*", 1))
    )
    .withExposedService(
      ZOOKEEPER,
      ZOOKEEPER_PORT,
      WaitAllStrategy(WITH_INDIVIDUAL_TIMEOUTS_ONLY)
        .withStrategy(forListeningPort())
        .withStrategy(forLogMessage(".*binding to port.*", 1))
    )
}
