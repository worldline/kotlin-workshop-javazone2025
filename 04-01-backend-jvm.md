# JVM frameworks for backend development

The most two popular JVM backend frameworks, namely Spring and Quarkus officially support Kotlin.
When using Kotlin on these frameworks, you retain all the benefits of the JVM ecosystem, such as Native image, Garbage collectors, Monitoring tools and Documentation.
Plus, you get all the benefits of the Kotlin language: Conciseness, Null safety, Extension functions, DSL and expressive test names.

To get started with Kotlin on these frameworks, you just need to selected Kotlin as the language when creating a new project with [Spring Initializr](https://start.spring.io/) or [Quarkus code generator](https://code.quarkus.io/).

## Spring guide part 1

Let's start with a simple REST API that provides CRUD operations on a `Customer` data class which is a stored in RAM in a `MutableList`.

- Create a project on [start.spring.io (also called Spring initializr)](https://start.spring.io/) with the following dependencies: Spring Web and Spring Boot DevTools.
- Choose Kotlin as the language and Kotlin-Grade as the project manager.
- Add these dependencies: **Spring Web**, **Spring Boot DevTools**, **h2 database** and **Spring Data JPA**.
- Click on "Generate". Download the archive, unzip it, and open the project with IntelliJ (preferably) or Android Studio.
- Create `Customer` data class in the `model` package.
- Create a `controller` package that contains a `CustomerController` class which provides a CRUD using a global list. You can find a skeleton below.

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
- Start the REST API server by running `.\gradlew bootRun` or from your IDE.
- Please test the endpoints with a REST client. You can find http files here in [JetBrains format](https://github.com/worldline/learning-kotlin/blob/main/material/spring-boot-kt-api/customer.jetbrains.http) or [VSCode's REST Client extension](https://github.com/worldline/learning-kotlin/blob/main/material/spring-boot-kt-api/customer.vscode-resclient.http)

### Spring boot part 2 - adding a database

Let's go a little bit further by storing data in a database and writing some tests.

We'll use the H2 in-memory database for the sake of simplicity, since it does not require a server to run.
Classes will map to database tables with JPA annotations.
The database API we'll be using is called `JPARepository`.
It is a lightweight API that provides common CRUD features by just defining an interface.

On the testing side, we'll see two different syntaxes.
The default one that is more familiar with Java style and the DSL one which is more readable and more familiar with Kotlin developers.

- Create a new Spring project using [Spring initializr](https://start.spring.io/) with Kotlin and the following dependencies: Spring Data JPA, H2 Database, Spring Boot DevTools, Spring Web
- Open the project and add this class in the `model` package `@Entity class Product(@Id @GeneratedValue var id: Long? = null, var name: String, var price: Int)`. This single defines the class as well as the minimal JPA annotations (`@Entity`, `@Id` and `@GeneratedValue`) to generate the corresponding table.
- In the `repository` package, declare the `ProductRepository` interface as follows `interface ProductRepository: JpaRepository<Product, Long>`. This is enough for Spring to generate an implementation with common features as we'll see later.
- Next, create a `ProductService` class which will contain the business logic. In terms of architecture, the controller calls a service which in turn rely on other services or repositories.

  ```kotlin
  @Service
  class ProductService(@Autowired val productRepository: ProductRepository) {
      fun getAll() = productRepository.findAll()
  
      // use findByIdOrNull instad of findById because the latter returns an optional<Product> instead of Product?
      fun getById(id: Long) = productRepository.findByIdOrNull(id)
  }
  ```

- In the controller package, create a `ProductController` class that is mapped to `/product` and injects the with `@Autowired`. Reply to `@Get` as follows.

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

  - To go further, Spring provides `@ControllerAdvice` to change the exception message. 
  You can see an [example here](https://spring.io/guides/tutorials/rest/).

### Tip: how kotlin makes code more concise

The Elvis operator `?:` allows to reduce the quantity of code as we have seen with:

```kotlin
fun getById(@PathVariable id: Long) =
          productService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
```

Here is a longer version as reference.

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

