package com.worldline.training

import kotlinx.serialization.Serializable

@Serializable
data class CountryInfoRequest(val country: String)

@Serializable
data class CountryInfoResponse(val response: String)
