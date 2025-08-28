package no.javazone.kotlinworkshop

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.all.simpleOpenAIExecutor

actual suspend fun getCountryInfo(countryName: String): String {
  val systemPrompt = "You give information about a given country"
  val agent = AIAgent(
    executor = simpleOpenAIExecutor("AIzaSyCURDDwX-5p6FQCUB1vekbbJowro_U6eHk"),
    systemPrompt = systemPrompt,
    llmModel = OpenAIModels.Chat.GPT4o
  )
  return agent.run(countryName)
}
