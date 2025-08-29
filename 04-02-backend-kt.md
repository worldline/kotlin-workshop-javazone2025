# Ktor server

Ktor is a cross-platform Kotlin library for building both HTTP clients and servers.
This makes Ktor a useful library to learn for both front-end developers for its HTTP client capabilities and backend-development for its HTTP server capabilities.

We'll focus here on the server part, and we'll create a REST API server.

## Practical work: develop an API with Ktor

- Create a project on [start.ktor.io](https://start.ktor.io/) with the following plugins: Content Negotiation, kotlinx.serialization, and Routing.
- Click on "Generate project".
- Download the archive, unzip it, and open the project with IntelliJ.
- Create a `models` package and add to it a `Customer` data class with these immutable properties `val id: String, val firstName: String, val lastName: String, val email: String`.
- Annotate the class with `@Serializable`.
- Create a new package named `routes` and add to it a file `CustomerRoutes.kt` that will contain the code for the `/customer` endpoint.
- The code below provides the implementation of some endpoints. Please implement the remaining ones.

  ```kotlin
  val store = mutableListOf<Customer>()
  
  fun Route.customerRouting() {
      route("/customer") {
          get {
              call.respond(store)
          }
          get("{id?}") {
              val id = call.parameters["id"] ?: return@get call.respondText(
                  "Missing id",
                  status = HttpStatusCode.BadRequest
              )
              val customer =
                  store.find { it.id == id } ?: return@get call.respondText(
                      "No customer with id $id",
                      status = HttpStatusCode.NotFound
                  )
              call.respond(customer)
          }
          post {
              val customer = call.receive<Customer>()
              store.add(customer)
              call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
          }
          delete("{id?}") {
  
          }
      }
  }
  ```

- To enable the route call `customerRouting()` in the routing configuration file located in `plugins/Routing.kt`.

  ```kotlin
  fun Application.configureRouting() {
      routing {
          customerRouting()
      }
  }
  ```

- For simplicity, use a global in-memory list of customers `val store = mutableListOf<Customer>()`.
- Run the server by running the main method.
- Test the api on the IDE by using an http file or using any other client.
- If needed, the following http file can be used to test the API with IntelliJ ultimate or VSCode: [04-backend/ktor-calls.http](./04-backend/ktor-calls.http)


## Tip: return@label

When many lambdas are nested, it can be useful to return from a specific lambda.
You can specify which level you want to return with an explicit label using `return@lambda`.

```kotlin
lambdaA {
    lambdaB {
        lambdaC {
            val randomInt = Random.nextInt(0, 100)
            if (randomInt > 50) return@lambdaC else return@lambdaB
        }
        printf("In lambdaB")
    }
}
```

Here is an example:

```kotlin
import kotlin.random.Random
fun main() {
   val kotlin = "🙂"
   kotlin.let {
       it.apply {
			val randomInt = Random.nextInt(0, 100)
            println(randomInt)
            if (randomInt > 50) return@apply else return@let
       }
       println("int let after apply")
   }
}
```

## Practical work: frontend + backend monorepo

The last compose exercise was about adding AI features to a compose app.
However, the way we did was not very secure since the API key was stored in the app.
Let's fix this by creating a backend that will handle the AI calls and provide its result through a rest API.

This creates a new problem, the REST API is public and can be called by anyone.
On the web side, this can be solved by CORS configuration.
However, for mobile and desktop apps, but there are some solutions for this as follows:

- Implement an authentication mechanism and allow only authenticated users to call the API through a token.
- Limit the number of calls per IP address.

Here the main steps for the practical work:

- Create a new [kmp project](https://kmp.jetbrains.com) that includes all front targets + server
- Open the project in IntelliJ or Android Studio. We can note two additional folders: 
  - *server*: a gradle module that contains the Ktor server application.
  - *shared*: contains the code that will be shared between the composeApp and the server.
- Each of these new folders contains a *build.gradle.kts* file, meaning that are gradle modules. 
  This implies that this project has overall 3 module (composeApp, server and shared)
- On the server, implement a REST API that exposes a POST route that takes a prompt and returns the AI result.
  - The request body can be like this: `{"prompt": "your prompt"}`.
  - The response body can be like this: `{"result": "the ai result"}`.
- Ohe frontend side implment again the same UI as in the previous exercise with these differences:
  - No need for a shared source set for android, wasmJs and jvm
  - No need for `koog` there (since it will be called from the backend)
  - The getAIResult function should call the backend API instead of calling Koog.
  - Remove the `expect` qualifier from `get***Info` and implement it directly in the common source set.
- The *share* module can contain the data classes for the request and response body.

## References

- [Official documentation](https://ktor.io/docs/creating-http-apis.html)
