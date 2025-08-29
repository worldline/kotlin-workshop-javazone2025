package no.javazone.kotlinworkshop

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor

actual suspend fun getCountryInfo(countryName: String): String {
  val agent = AIAgent(
    executor = simpleGoogleAIExecutor(AI_API_KEY),
    systemPrompt = systemPrompt,
    llmModel = GoogleModels.Gemini2_0FlashLite
  )
  return agent.run(countryName)
}
