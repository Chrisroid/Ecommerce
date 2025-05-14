package com.chrisroid.ecommerce.features.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chrisroid.ecommerce.data.local.entities.Product
import com.chrisroid.ecommerce.data.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.productId == product.id }

        if (existingItem != null) {
            currentItems[currentItems.indexOf(existingItem)] = existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        } else {
            currentItems.add(
                CartItem(
                    productId = product.id,
                    quantity = 1,
                    name = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl
                )
            )
        }

        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filterNot { it.productId == productId }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
            return
        }

        _cartItems.value = _cartItems.value.map { item ->
            if (item.productId == productId) {
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    private val _paymentStatus = MutableLiveData<PaymentStatus>(PaymentStatus.NONE)
    val paymentStatus: LiveData<PaymentStatus> = _paymentStatus

    sealed class PaymentStatus {
        object NONE : PaymentStatus()
        object PROCESSING : PaymentStatus()
        data class SUCCESS(val reference: String) : PaymentStatus()
        data class FAILED(val message: String) : PaymentStatus()
    }

    fun setPaymentProcessing() {
        _paymentStatus.value = PaymentStatus.PROCESSING
    }

    fun setPaymentSuccess(reference: String) {
        _paymentStatus.value = PaymentStatus.SUCCESS(reference)
    }

    fun setPaymentFailed(message: String) {
        _paymentStatus.value = PaymentStatus.FAILED(message)
    }

    fun resetPaymentStatus() {
        _paymentStatus.value = PaymentStatus.NONE
    }
}