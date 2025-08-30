package com.worldline.training

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(val name: String, val url: String)

@Serializable
data class PokeApiPage(val count: Int, val next: String, val previous: String?, val results: List<Pokemon>)
