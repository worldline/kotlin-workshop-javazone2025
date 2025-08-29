# JVM frameworks for backend development

The two most popular JVM backend frameworks, namely Spring and Quarkus, officially support Kotlin.
When using Kotlin on these frameworks, you retain all the benefits of the JVM ecosystem, such as native image, garbage collectors, monitoring tools, and documentation.
Plus, you get all the benefits of the Kotlin language: conciseness, null safety, extension functions, DSLs, and expressive test names.

To get started with Kotlin on these frameworks, you just need to select Kotlin as the language when creating a new project with [Spring Initializr](https://start.spring.io/) or [Quarkus code generator](https://code.quarkus.io/).

## Spring guide part 1 - simple REST API

Let's start with a simple REST API that provides CRUD operations on a `Customer` data class which is stored in RAM in a `MutableList`.

- Create a project on [start.spring.io (also called Spring Initializr)](https://start.spring.io/).
  - Choose Kotlin as the language and Gradle with Kotlin DSL as the project manager.
  - Add these dependencies: **Spring Web**, **Spring Boot DevTools**, **H2 Database** and **Spring Data JPA**.
- Click on "Generate". Download the archive, unzip it, and open the project with IntelliJ (preferably) or Android Studio.
- Create a `Customer` data class in the `model` package, with the following fields: `val id: String, val firstName: String, val lastName: String, val email: String`.
- Create a `controller` package that contains a `CustomerController` class which provides CRUD using a global list. You can find a skeleton below.

  ```kotlin
  val store = mutableListOf<Customer>()
  
  @RestController
  @RequestMapping("/customer")
  class CustomerController {
      @GetMapping
      fun getAll() = store
  
      @GetMapping("{id}")
      fun getById(@PathVariable id: String) { /* TODO: implement */ }
  
      @PostMapping
      fun addOne(@RequestBody customer: Customer) { /* TODO: implement */ }
  
      @DeleteMapping("{id}")
      fun deleteOne(@PathVariable id: String) { /* TODO: implement */ }
  }
  ```

- Implement the missing parts.
- Start the REST API server by running `./gradlew bootRun` or from your IDE.
- Please test the endpoints with a REST client. You can find HTTP files here in [JetBrains format](https://github.com/worldline/learning-kotlin/blob/main/material/spring-boot-kt-api/customer.jetbrains.http) or [VSCode's REST Client extension](https://github.com/worldline/learning-kotlin/blob/main/material/spring-boot-kt-api/customer.vscode-resclient.http)

## Spring guide part 2 - In-memory database

Let's go a little bit further by storing data in a database and writing some tests.

We'll use the H2 in-memory database for the sake of simplicity, since it does not require a server to run.
Classes will map to database tables with JPA annotations.
The database API we'll be using is called `JpaRepository`.
It is a lightweight API that provides common CRUD features by just defining an interface.

On the testing side, we'll see two different syntaxes.
The default one that is more familiar with Java style and the DSL one which is more readable and more familiar with Kotlin developers.

- Continue on the existing project or create a new Spring project using [Spring Initializr](https://start.spring.io/) with Kotlin and the following dependencies: Spring Data JPA, H2 Database, Spring Boot DevTools, Spring Web.
- Open the project and add this class in the `model` package `@Entity class Product(@Id @GeneratedValue var id: Long? = null, var name: String, var price: Int)`. This single line defines the class as well as the minimal JPA annotations (`@Entity`, `@Id` and `@GeneratedValue`) to generate the corresponding table.
- In the `repository` package, declare the `ProductRepository` interface as follows `interface ProductRepository: JpaRepository<Product, Long>`. This is enough for Spring to generate an implementation with common features as we'll see later.
- Next, create a `ProductService` class which will contain the business logic. In terms of architecture, the controller calls a service which in turn relies on other services or repositories.

  ```kotlin
  @Service
  class ProductService(@Autowired val productRepository: ProductRepository) {
      fun getAll() = productRepository.findAll()
  
      // use findByIdOrNull instead of findById because the latter returns an Optional<Product> instead of Product?
      fun getById(id: Long) = productRepository.findByIdOrNull(id)
  }
  ```

- In the controller package, create a `ProductController` class that is mapped to `/product` and injects the service with `@Autowired`. Reply to `@Get` as follows.

  ```kotlin
  @RestController
  @RequestMapping("/product")
  class ProductController(@Autowired val productService: ProductService) {
      @GetMapping fun getAll() = productService.getAll()
  
      @GetMapping("{id}")
      fun getById(@PathVariable id: Long) =
          productService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
  }
  ```

  - It is possible to change the exception message with the `@ControllerAdvice` annotation. 
  You can see an [example here](https://spring.io/guides/tutorials/rest/).

- Before running the project, we need to add a plugin that allows Kotlin classes to generate a default constructor: `id("org.jetbrains.kotlin.plugin.jpa") version "1.8.10"`. The plugins should look as follows:

  ```kotlin
  plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.10"
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
  }
  ```

- As an exercise, implement these endpoints: POST a single product, DELETE by id (`/product/{id}`), and GET by id (`/product/{id}`).
  - Hint: `ProductController` already provides the necessary methods.
- If the server did not hot reload, restart it.
- Call the different endpoints with a REST client.

### Tip: how Kotlin makes code more concise

The Elvis operator `?:` allows us to reduce the amount of code as we have seen with:

```kotlin
fun getById(@PathVariable id: Long) =
          productService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
```

Here is a longer version as a reference:

```kotlin
@GetMapping("{id}")
fun getById(@PathVariable id: Long): Product {
    val product = productService.getById(id)
    if (product != null){
        return product
    }
    throw ResponseStatusException(HttpStatus.NOT_FOUND)
}
```

### Spring guide part 3 - tests

Spring frameworks help perform different types of tests by providing different classes out of the box:

- Unit testing of services, repositories, and the REST API. This is done through mock utilities such as `MockMvc`.
- Integration testing of the REST API using `TestRestTemplate`. In this situation, a full server is run and tested.

Most, if not all, classes provided by Spring offer an elegant syntax for Java developers.
Some of them go further by taking advantage of Kotlin-specific features.
In the following, we're going to focus on parts that provide Kotlin DSLs, namely unit testing the REST API with `MockMvc`.

- Create a test class `ProductControllerUnitTests` with this initial content. `MockMvc` allows you to unit test the REST API. The `@AutoConfigureMockMvc` annotation allows Spring to configure it automatically.

  ```kotlin
  @SpringBootTest
  @AutoConfigureMockMvc
  class ProductControllerTests(
      @Autowired val mockMvc: MockMvc,
      @Autowired val productRepository: ProductRepository) {
  
      @BeforeEach
      fun reset(){
          productRepository.deleteAll()
      }
  }
  ```

- Add these two tests. The first one uses a classic approach while the second one takes advantage of Kotlin DSL capabilities.
  In addition to that, we name the test using a more readable string literal.

  ```kotlin
  @Test
  fun testWithClassicApproach(){
      mockMvc.perform(get("/product"))
          .andExpect(status().isOk)
          .andExpect(content().string(containsString("[]")))
  }
  ```

  ```kotlin
  @Test
  fun `test GET a single product`() {
      mockMvc.get("/product/1").andExpect {
          status { isOk() }
          jsonPath("$.name") { value("A") }
          jsonPath("$.price") { value(1) }
          content { contentType(MediaType.APPLICATION_JSON) }
      }
  }
  ```

- As an exercise, write unit tests for the other endpoints.

### Tip: the request builder of JpaRepository

Spring repositories implement requests based on the names of their methods.
For example, to get all products sorted by name, we can add this method to the interface:

```kotlin
interface ProductRepository: JpaRepository<Product, Long> {
    fun findAllByOrderByNameAsc(): List<Product>
}
```

[The official documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation) provides more detailed explanations and examples.
