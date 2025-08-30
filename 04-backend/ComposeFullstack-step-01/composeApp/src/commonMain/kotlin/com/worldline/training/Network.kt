package com.worldline.training

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient {
  install(ContentNegotiation) {
    json(json = Json { ignoreUnknownKeys = true })
  }
}

suspend fun fetchFirstPage(): PokeApiPage {
  val url = "https://pokeapi.co/api/v2/pokemon/"
  val response = client.get(url)
  return response.body()
}

suspend fun getInfoFromAi(name: String): String {
  val url = if (getPlatform().name == "Web with Kotlin/Wasm") "/api" else "http://localhost:8080/api"
  val response = client.post(url) {
    setBody(AiInfoRequestBody(name))
    contentType(ContentType.Application.Json)
  }
  val aiInfoResponseBody = response.body<AiInfoResponseBody>()
  return aiInfoResponseBody.response
}
