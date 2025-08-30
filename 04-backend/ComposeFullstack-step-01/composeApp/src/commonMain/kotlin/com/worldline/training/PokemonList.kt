package com.worldline.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PokemonList() {
  var pokemons by remember { mutableStateOf(listOf<Pokemon>()) }
  var selectedName by remember { mutableStateOf("") }
  LaunchedEffect(Unit) {
    pokemons = fetchFirstPage().results
  }
  Column(horizontalAlignment = CenterHorizontally) {
    PokemonInfo(selectedName)
    LazyColumn {
      items(pokemons) {
        Box(Modifier.clickable { selectedName = it.name }) {
          PokemonRow(it)
        }
      }
    }
  }
}

@Composable
fun PokemonRow(pokemon: Pokemon) {
  Row(modifier = Modifier.height(80.dp).padding(3.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
    val id = pokemon.url.trimEnd('/').split("/").last().toInt()
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    AsyncImage(imageUrl, contentDescription = pokemon.name)
    Text(pokemon.name)
  }
}

@Preview
@Composable
fun PokemonListPreview() {
  PokemonList()
}

@Preview
@Composable
fun PokemonRowPreview() {
  PokemonRow(
    Pokemon("Bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
  )
}
