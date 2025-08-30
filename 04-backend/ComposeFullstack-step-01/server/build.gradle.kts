plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.ktor)
  application
}

group = "com.worldline.training"
version = "1.0.0"
application {
  mainClass.set("com.worldline.training.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
  implementation(projects.shared)
  implementation(libs.logback)
  implementation(libs.bundles.ktor.server)
  implementation(libs.koog.agents)
  testImplementation(libs.ktor.serverTestHost)
  testImplementation(libs.kotlin.testJunit)
}

ktor {
  docker {
    externalRegistry.set(
      io.ktor.plugin.features.DockerImageRegistry.dockerHub(
        appName = provider { "jz25-kotlin-workshop" },
        username = providers.environmentVariable("CONTAINER_REGISTRY_USERNAME"),
        password = providers.environmentVariable("CONTAINER_REGISTRY_PASSWORD")
      )
    )
  }
}
