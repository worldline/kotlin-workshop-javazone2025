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

suspend fun fetchCountries(): List<Country> {
  val response = client.get("https://restcountries.com/v3.1/all?fields=name,flag,flags,capital")
  return response.body()
}

suspend fun fetchCountryInfo(countryName: String): String {
  val url = if (getPlatform().name == "Web with Kotlin/Wasm") "/api" else "http://localhost:8080/api"
  val response = client.post(url) {
    setBody(CountryInfoRequest(countryName))
    contentType(ContentType.Application.Json)
  }
  val countryInfoResponse = response.body<CountryInfoResponse>()
  return countryInfoResponse.response
}
