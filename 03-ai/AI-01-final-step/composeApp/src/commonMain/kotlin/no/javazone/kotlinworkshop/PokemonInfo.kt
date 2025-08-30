package no.javazone.kotlinworkshop

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview

const val systemPrompt =
  "You give information a pokemon given its name or id. Please answer in concise manner that fits in one line."

suspend fun getInfoFromAi(name: String): String {
  val agent = AIAgent(
    executor = simpleGoogleAIExecutor(AI_API_KEY),
    systemPrompt = systemPrompt,
    llmModel = GoogleModels.Gemini2_0FlashLite
  )
  return agent.run(name)
}

@Composable
fun PokemonInfo(name: String) {
  var aiResult by remember { mutableStateOf("") }
  LaunchedEffect(name) {
    aiResult = ""
    if (name.isNotBlank()) {
      aiResult = getInfoFromAi(name)
    }
  }
  Text("$name info from AI : $aiResult")
}

@Preview
@Composable
fun PreviewPokemonInfo() {
  PokemonInfo("Budew")
}
