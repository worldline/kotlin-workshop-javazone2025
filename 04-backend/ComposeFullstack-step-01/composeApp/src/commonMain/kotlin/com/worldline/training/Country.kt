package com.worldline.training

import kotlinx.serialization.Serializable

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
