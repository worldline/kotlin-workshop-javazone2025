package com.worldline.training

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview

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
