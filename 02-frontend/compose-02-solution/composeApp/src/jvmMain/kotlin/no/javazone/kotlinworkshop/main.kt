package no.javazone.kotlinworkshop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "JavaZone2025",
    ) {
        App()
    }
}