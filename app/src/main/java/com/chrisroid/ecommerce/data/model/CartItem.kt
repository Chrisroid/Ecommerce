package com.chrisroid.ecommerce.data.model

data class CartItem(
    val productId: String,
    val quantity: Int,
    val name: String,
    val price: Double,
    val imageUrl: String
)