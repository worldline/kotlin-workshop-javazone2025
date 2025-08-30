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
data class Pokemon(val name: String, val url: String)

@Serializable
data class PokeApiPage(val count: Int, val next: String, val previous: String?, val results: List<Pokemon>)

val httpClient = HttpClient {
  install(ContentNegotiation) {
    json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
  }
}

const val url = "https://pokeapi.co/api/v2/pokemon/"

suspend fun fetchFirstPage(): PokeApiPage {
  val response = httpClient.get(url)
  return response.body()
}

@Composable
@Preview
fun App() {
  LaunchedEffect(Unit) {
    val pokemons = fetchFirstPage().results
    println(pokemons)
  }
  MaterialTheme {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text("Kotlin workshop")
    }
  }
}
