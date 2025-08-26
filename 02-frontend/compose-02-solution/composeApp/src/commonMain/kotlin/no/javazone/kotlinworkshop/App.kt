package no.javazone.kotlinworkshop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview

val httpClient = HttpClient {
  install(ContentNegotiation) {
    json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
  }
}

@Composable
@Preview
fun App() {
  LaunchedEffect(Unit) {
    val response = httpClient.get("https://restcountries.com/v3.1/all?fields=name,flag,flags,capital")
    val body = response.body<String>()
    println(body)
  }
  MaterialTheme {
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer)
        .safeContentPadding()
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      CountryList()
    }
  }
}
