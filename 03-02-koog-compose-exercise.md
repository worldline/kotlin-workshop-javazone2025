# Exercise: Request details from AI

In this exercise, we ask you to develop a compose UI app that requests information from a LLM.
More precisely, we'll continue with the compose app that we are developing which shows a list of Pokémons.
When the user selects an item in the list, we'll request information about the selecteditem from a LLM using the Koog library.
The information will be shown in a `Text` above the `LazyColumn`.

You can continue with the app that you worked on, or start from a solution available here: [02-frontend/compose-03-final-step](02-frontend/compose-03-final-step/)

⚠️ We'll be hardcoding the api key in a front-end which a red alert security practice.
Please be careful not to push it to a public repository and to not include api keys in public fronts, whether they are, mobile, desktop or web.
It is possible to do it for servers and for non-public applications.
What we are going to do is just for the sake of example.

- In the version catalog, add the dependency to the Koog library:

  ```toml
  koog = "0.4.0"

  [libraries] 
  koog-agents = { module = "ai.koog:koog-agents", version.ref = "koog" }
  ```
  
- In the `commonMain` sourceSet, define the `PokemonInfo.kt` file that defines the composable that will show the AI response.
  
  ```kotlin
  @Composable
  fun PokemonInfo(name: String) {
    var aiResult by remember { mutableStateOf("") }
    LaunchedEffect(name) {
      aiResult = ""
      if (name.isNotBlank()) {
        aiResult = getInfoFromAi(name)
      }
    }
    Text("$name info from AI : $aiResult")
  }
  
  @Preview
  @Composable
  fun PreviewPokemonInfo() {
    PokemonInfo("Budew")
  }
  ```

- The `PokemonInfo` composable uses a `getInfoFromAi` function to get information of Pokémon.
- In the same `PokemonInfo.kt` file, define the `getInfoFromAi` function:

  ```kotlin
  const val systemPrompt =
    "You give information a pokemon given its name or id. Please answer in concise manner that fits in one line."
  
  suspend fun getInfoFromAi(name: String): String {
    val agent = AIAgent(
      executor = simpleGoogleAIExecutor(AI_API_KEY),
      systemPrompt = systemPrompt,
      llmModel = GoogleModels.Gemini2_0FlashLite
    )
    return agent.run(name)
  }
  ```

- ⚠️ Please be careful about the `AI_API_KEY` variable because you *must never* hardcode-it in public apps, unless it is a non-public applications, for learning purposes or a servers.
  The safest way is to provide AI information through a backend that you control (we'll do this in a future exercise).
  We hardcode the Api Key just for the sake of simplification.
- Next, change the `PokemonList` composable so that it shows a button next to each row. When the button is clicked, the `PokemonyInfo` composable is shown with the information about the selected item.

  ```kotlin
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
  ```

- Run the app on the different platform to see the result.

A solution is available here: [03-ai/AI-01-final-step](./03-ai/AI-01-final-step)
