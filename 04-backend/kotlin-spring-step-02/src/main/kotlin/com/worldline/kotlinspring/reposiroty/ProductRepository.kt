package com.worldline.kotlinspring.reposiroty

import com.worldline.kotlinspring.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long>
