package com.worldline.training

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
  io.ktor.server.netty.EngineMain.main(args)
}

val agent = AIAgent(
  executor = simpleGoogleAIExecutor(System.getenv("GOOGLEAI_API_KEY")),
  systemPrompt = "You give information about a Pokémon given its name. Please answer in concise manner that fits in one line.",
  llmModel = GoogleModels.Gemini2_0FlashLite
)

fun Application.module() {
  install(ContentNegotiation) {
    json()
  }
  routing {
    route("/api") {
      post {
        val request = call.receive<AiInfoRequestBody>()
        val result = agent.run(request.name)
        call.respond(AiInfoResponseBody(result))
      }
    }
    staticResources("/", "static")
  }
}
