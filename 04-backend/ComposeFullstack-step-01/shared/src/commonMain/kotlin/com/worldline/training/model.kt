package com.worldline.training

import kotlinx.serialization.Serializable

@Serializable
data class AiInfoRequestBody(val name: String)

@Serializable
data class AiInfoResponseBody(val response: String)
