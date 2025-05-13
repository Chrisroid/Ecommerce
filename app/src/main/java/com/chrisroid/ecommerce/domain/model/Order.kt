package com.chrisroid.ecommerce.domain.model

import com.chrisroid.ecommerce.data.model.CartItem
import com.google.firebase.Timestamp

data class Order(
    val id: String = "",
    val userId: String,
    val items: List<CartItem>,
    val total: Double,
    val status: OrderStatus = OrderStatus.PENDING,
    val createdAt: Timestamp = Timestamp.now(),
    val shippingAddress: String = "",
    val paymentMethod: String = ""
)

enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}