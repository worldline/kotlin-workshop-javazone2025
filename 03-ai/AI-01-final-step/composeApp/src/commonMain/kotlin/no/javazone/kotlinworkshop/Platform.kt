package no.javazone.kotlinworkshop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform