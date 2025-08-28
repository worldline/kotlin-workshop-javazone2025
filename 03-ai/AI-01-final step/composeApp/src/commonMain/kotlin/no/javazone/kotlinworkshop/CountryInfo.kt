package no.javazone.kotlinworkshop

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview

const val systemPrompt =
  "You give information about a given country. Please answer in concise manner that fits in one line."

expect suspend fun getCountryInfo(countryName: String): String

@Composable
fun CountryInfo(countryName: String) {
  var countryInfo by remember { mutableStateOf("") }
  LaunchedEffect(countryName) {
    countryInfo = ""
    if (countryName.isNotBlank()) {
      countryInfo = getCountryInfo(countryName)
    }
  }
  Text("Country $countryName info from AI : $countryInfo")
}

@Preview
@Composable
fun PreviewCoutryInfo() {
  CountryInfo("France")
}
