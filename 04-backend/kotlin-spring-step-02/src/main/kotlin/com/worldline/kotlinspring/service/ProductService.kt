package com.worldline.kotlinspring.service

import com.worldline.kotlinspring.model.Product
import com.worldline.kotlinspring.reposiroty.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(@Autowired val productRepository: ProductRepository) {
  fun getAll() = productRepository.findAll()

  // use findByIdOrNull instead of findById because the later returns an Optional<Product> instead of Product?
  fun getById(id: Long) = productRepository.findByIdOrNull(id)
  fun add(product: Product) { productRepository.save(product) }
  fun delete(id: Long) { productRepository.deleteById(id) }
}
