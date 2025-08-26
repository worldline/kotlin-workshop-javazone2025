package no.javazone.kotlinworkshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
data class CountryFlagCatalog(val png: String, val svg: String, val alt: String)

@Serializable
data class CountryName(val common: String, val official: String)

@Serializable
data class Country(
  val name: CountryName,
  val flag: String,
  val flags: CountryFlagCatalog,
  val capital: List<String>
)

val httpClient = HttpClient {
  install(ContentNegotiation) {
    json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
  }
}

suspend fun fetchCountries(): List<Country> {
  val response = httpClient.get("https://restcountries.com/v3.1/all?fields=name,flag,flags,capital")
  return response.body() // This will be parsed to List<Country> and is inferred from the return type
}

@Composable
@Preview
fun App() {
  LaunchedEffect(Unit) {
    val countries = fetchCountries()
    println(countries)
  }
  MaterialTheme {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text("Country list Application")
    }
  }
}
