package com.worldline.training

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform