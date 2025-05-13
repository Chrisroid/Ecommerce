package com.chrisroid.ecommerce.data.repository

import com.chrisroid.ecommerce.domain.model.Order
import com.chrisroid.ecommerce.features.auth.Resource

interface OrderRepository {
    suspend fun placeOrder(order: Order): Resource<Order>
    suspend fun getOrders(userId: String): Resource<List<Order>>

}