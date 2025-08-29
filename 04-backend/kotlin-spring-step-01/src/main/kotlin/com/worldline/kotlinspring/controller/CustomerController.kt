package com.worldline.kotlinspring.controller

import com.worldline.kotlinspring.model.Customer
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val store = mutableListOf<Customer>()

@RestController
@RequestMapping("/customer")
class CustomerController {
  @GetMapping
  fun getAll() = store

  @GetMapping("{id}")
  fun getById(@PathVariable id: String) = store.first { it.id == id }

  @PostMapping
  fun addOne(@RequestBody customer: Customer) {
    store.add(customer)
  }

  @DeleteMapping("{id}")
  fun deleteOne(@PathVariable id: String) {
    store.removeIf { it.id == id }
  }
}
