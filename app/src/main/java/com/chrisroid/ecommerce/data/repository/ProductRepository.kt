package com.chrisroid.ecommerce.data.repository

import com.chrisroid.ecommerce.data.local.entities.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun refreshProducts()
}