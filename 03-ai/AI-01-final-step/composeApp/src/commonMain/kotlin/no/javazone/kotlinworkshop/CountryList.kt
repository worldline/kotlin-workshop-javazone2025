package no.javazone.kotlinworkshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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

val client = HttpClient {
  install(ContentNegotiation) {
    json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
  }
}

suspend fun fetchData(): List<Country> {
  val response = client.get("https://restcountries.com/v3.1/all?fields=name,flag,flags,capital")
  return response.body()
}

@Composable
fun CountryList() {
  var countries by remember { mutableStateOf(listOf<Country>()) }
  var selectedCountryName by remember { mutableStateOf("") }
  LaunchedEffect(Unit) {
    countries = fetchData()
  }
  Column {
    CountryInfo(selectedCountryName)
    LazyColumn {
      items(countries) { country ->
        Row {
          Button(onClick = { selectedCountryName = country.name.official }) {
            Text("Select")
          }
          CountryItem(country)
        }
      }
    }
  }
}

@Composable
fun CountryItem(country: Country) {
  Row(modifier = Modifier.height(60.dp).padding(3.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
    AsyncImage(model = country.flags.svg, contentDescription = country.flags.alt)
    Text(country.flag)
    Column {
      Text(country.name.official, fontWeight = FontWeight.Bold)
      Text(
        country.capital.joinToString(", "),
        maxLines = 1
      )
    }
  }
}

@Preview
@Composable
fun CountryListPreview() {
  CountryList()
}

@Preview
@Composable
fun CountryItemPreview() {
  // generate country from this json {"flags":{"png":"https://flagcdn.com/w320/no.png","svg":"https://flagcdn.com/no.svg","alt":"The flag of Norway has a red field with a large white-edged navy blue cross that extends to the edges of the field. The vertical part of this cross is offset towards the hoist side."},"name":{"common":"Norway","official":"Kingdom of Norway","nativeName":{"nno":{"official":"Kongeriket Noreg","common":"Noreg"},"nob":{"official":"Kongeriket Norge","common":"Norge"},"smi":{"official":"Norgga gonagasriika","common":"Norgga"}}},"capital":["Oslo"],"flag":"🇳🇴"}
  val country = Country(
    name = CountryName(
      common = "Norway",
      official = "Kingdom of Norway"
    ),
    flag = "🇳🇴",
    flags = CountryFlagCatalog(
      png = "https://flagcdn.com/w320/no.png",
      svg = "https://flagcdn.com/no.svg",
      alt = "The flag of Norway has a red field with a large white-edged navy blue cross that extends to the edges of the field. The vertical part of this cross is offset towards the hoist side."
    ),
    capital = listOf("Oslo")
  )
  CountryItem(country)
}

