package com.chrisroid.ecommerce.data.repository

import com.chrisroid.ecommerce.domain.model.Order
import com.chrisroid.ecommerce.features.auth.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : OrderRepository {

    override suspend fun placeOrder(order: Order): Resource<Order> {
        return try {
            val document = firestore.collection("orders").document()
            val orderWithId = order.copy(id = document.id)
            document.set(orderWithId).await()
            Resource.Success(orderWithId)
        } catch (e: Exception) {
            Resource.Error("Failed to place order: ${e.message}")
        }
    }

    override suspend fun getOrders(userId: String): Resource<List<Order>> {
        return try {
            val orders = firestore.collection("orders")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(Order::class.java)
            Resource.Success(orders)
        } catch (e: Exception) {
            Resource.Error("Failed to get orders: ${e.message}")
        }
    }
}