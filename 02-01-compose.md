# Compose Multiplatform

Compose Multiplatform is the cross-platform equivalent of Compose for Android (which is the current official UI kit for
Android).
It runs on iOS, Android, the web and desktop (through the JVM).
Thus, it really allows Kotlin to be a _write once, run everywhere_ language.

The current best way to get started in with the [Kotlin playground](https://play.kotlinlang.org/) which renders UI
components in the browser thanks to the support of the web platform.

## First compose App

Let's start by running our first Compose Multiplatform application in the Kotlin playground:

1. Please open [this link to load a sample app](https://pl.kotl.in/gafzm3lxw).
1. Click on the "Run" button to execute the code.
1. You should see a simple UI with a button. Click on the button once and then click it another time.
1. Read the code and analyze how the components are structured.

Compose works a declarative way simialr to React:

- You define the UI in a function using a syntax similar to HTML.
  - In reality, it is a composition of Higher Order Functions (parenthesis are omitted as we have seen in the first part).
  - The first letter is capitalized, which is a convention in Compose (also similar to React).
  - The function must have the `Composable` annotation.
- Any element that changes in the UI must as a **state** using this syntax:
  `var myStateVar by remember { mutableStateOf("Hello World!") }`

The general form of a composable function is:

```kotlin
@Composable
fun MyComposable() {
  var myStateVar by remember { mutableStateOf(initialValue) }
  UiElementWithParams(params) {
    UiElementWithNoParams {
      // Nested UI elements
    }
    UiElementWithParams(params) {
      // Nested UI elements
    }
    // ...
  }
}
```

## Analyzing the code

Let's analyse the `App` function:

```kotlin
@Composable // 1
fun App() { // 2
  MaterialTheme { // 3
    var greetingText by remember { mutableStateOf("Hello World!") } // 4
    var showImage by remember { mutableStateOf(false) } // 5
    var counter by remember { mutableStateOf(0) } // 6
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) { // 7
      Button(onClick = { // 8
        counter++
        greetingText = "Compose: ${Greeting().greet()}"
        showImage = !showImage
      }) { // 9
        Text(greetingText)
      }
      AnimatedVisibility(showImage) { // 10
        Text(counter.toString())  // 11
      }
    }
  }
}
```

1. The `@Composable` annotation indicates that this function is a composable function, which means it can be used to build the UI.
2. The function `App` is defined, which will be the entry point of our UI (called from the `main` function).
3. `MaterialTheme` is a composable function that provides a Material Design theme for the UI. All the UI are embedded in this theme to ensure consistent styling.
4. `greetingText` is a mutable state variable initialized with `"Hello World!"`. This means that whenever this variable changes, the UI will be recomposed to reflect the new value.
5. `showImage` is also a mutable state variable initialized with `false`, which will control the visibility of the image.
6. `counter` is also a mutable state variable initialized with `0`, which will be used to count the number of button clicks.
7. `Column` is a composable function that arranges its children in a vertical column. The `Modifier.fillMaxWidth()` makes the column take the full width of the parent, and `horizontalAlignment = Alignment.CenterHorizontally` centers the children horizontally.
8. `Button` is a composable function that creates a button. The `onClick` lambda is executed when the button is clicked.
9. Inside the button, we display the `greetingText` using a `Text` composable function.
10. `AnimatedVisibility` is a composable function that controls the visibility of its child based on the `showImage` state. If `showImage` is `true`, the child will be visible, otherwise it will be hidden.
11. Inside the `AnimatedVisibility`, we display the `counter` value as a `Text` composable function.

## Exercises

1. Add a text that displays `JavaZone 2025` above the button.
2. Make the counter start at `2025`

## List + Changing the button behaviour

Let's add a list and change the button behaviour to add an item to the list instead of changing the greeting text.

1. Add these imports:

  ```kotlin
  import androidx.compose.foundation.lazy.LazyColumn
  import androidx.compose.foundation.lazy.items
  ```

1. Define a list variable after the `counter`: `var messages by remember { mutableStateOf(listOf("Hello", "Compose")) }`
1. Add a `LazyColumn` inside the `Column` after the `AnimatedVisibility`:

  ```kotlin
  LazyColumn {
    items(messages) { message ->
      Text(message)
    }
  }
  ```

1. Run the app to verify that the list is displayed.
1. Change the button `onClick` lambda to add a new item to the list instead of changing the greeting text:

  ```kotlin
  onClick = {
    counter++
    messages += "Item $counter"
    showImage = !showImage
  }
  ```

1. Let's finish by styling the list items by adding a padding to the `Text` inside the `LazyColumn`.
    - Modify the `Text` inside the `LazyColumn` to:

      ```kotlin
      Text(message, modifier = Modifier.padding(8.dp))
      ```

    - Add the necessary imports:

      ```kotlin
      import androidx.compose.foundation.layout.padding
      import androidx.compose.ui.unit.dp
      ```

## Local development

Once you are familiar with the basics, you can use the [Kotlin Multiplatform template](https://kmp.jetbrains.com/) which
generates starter projects.
From this page, generate a new project with the following options (Alternatively, you can use this link to download
the [project template directly](https://kmp.jetbrains.com/generateKmtProject?name=JavaZone2025&id=no.javazone.kotlinworkshop&spec=%7B%22template_id%22%3A%22kmt%22%2C%22targets%22%3A%7B%22android%22%3A%7B%22ui%22%3A%5B%22compose%22%5D%7D%2C%22ios%22%3A%7B%22ui%22%3A%5B%22compose%22%5D%7D%2C%22desktop%22%3A%7B%22ui%22%3A%5B%22compose%22%5D%7D%2C%22web%22%3A%7B%22ui%22%3A%5B%22compose%22%5D%7D%7D%2C%22include_tests%22%3Atrue%7D)):

- Project Name: `JavaZone 2025 Kotlin workshop`
- Project ID: `no.javazone.kotlinworkshop`
- Platforms: Android, iOS (with shared UI), desktop, web (with shared UI)

Once the project is downloaded, unzip it and open it in IntelliJ IDEA or Android Studio.
Next, the IDE takes some time to load the project and download the necessary dependencies.

While the project loads, let's analyze its structure:

- **gradle/libs.versions.toml**: is the central place that lists all the version libraries and plugins of the project. It is called a _version catalog_.
  - In this file, we can note the presence of the Kotlin Multiplatform plugin `kotlinMultiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }` which handles the compilation of the project into the different targets.
- **build.gradle.kts**: loads all the plugins listed in the version catalog.
- **iosApp**: iOS specific code (in swift) and configuration files. This can be left as is.
- **composeApp**: the application module that contains Kotlin code for the Compose UI.
  - **composeApp/src/commonMain** and **composeApp/src/commonTest**: shared code and resources for all platforms, while the other folders in the **composeApp/src** directory contain platform-specific code.
  - **composeApp/build.gradle.kts**: the Gradle build file for the Compose application. It contains common build configuration as well as platform specific ones.
  - The **commonMain** folder, **commonTest** folder where we want to put most of our code, files and dependencies.

## Going further

Compose Multiplatform provides a [great documentation](https://www.jetbrains.com/compose-multiplatform/) to continue learning.
In addition to that, since Compose Multiplatform is based on Jetpack Compose, you can even refer to [Jetpack Compose documentation](https://developer.android.com/develop/ui/compose/documentation) to go further.
