package com.worldline.kotlinspring.controller

import com.worldline.kotlinspring.model.Product
import com.worldline.kotlinspring.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/product")
class ProductController(@Autowired val productService: ProductService) {
  @GetMapping
  fun getAll() = productService.getAll()

  @GetMapping("{id}")
  fun getById(@PathVariable id: Long) =
    productService.getById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

  @PostMapping
  fun add(@RequestBody product: Product) {
    productService.add(product)
  }

  @DeleteMapping("{id}")
  fun delete(@PathVariable id: Long) {
    productService.delete(id)
  }
}
