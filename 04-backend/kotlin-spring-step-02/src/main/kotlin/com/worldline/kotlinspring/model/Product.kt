package com.worldline.kotlinspring.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Product(
  @Id @GeneratedValue var id: Long? = null,
  var name: String,
  var price: Int
)
