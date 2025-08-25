package org.example

import javafx.application.Application
import javafx.stage.Stage
import ktfx.coroutines.onAction
import ktfx.dialogs.infoAlert
import ktfx.launchApplication
import ktfx.layouts.button
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.textField
import ktfx.layouts.scene


class MyApplication : Application() {
    override fun start(stage: Stage) {
        stage.scene {
            gridPane {
                hgap = 10.0
                vgap = 10.0
                label("First name").grid(0, 0)
                val firstName = textField().grid(0, 1)
                label("Last name").grid(1, 0)
                val lastName = textField().grid(1, 1)
                button("Say hello") {
                    onAction {
                        infoAlert("Hello, ${firstName.text} ${lastName.text}!") {}
                    }
                }
            }
        }
        stage.show()
    }
}

fun main() {
    launchApplication<MyApplication>()
}
