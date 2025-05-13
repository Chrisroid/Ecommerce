package com.chrisroid.ecommerce.features.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chrisroid.ecommerce.data.repository.OrderRepository
import com.chrisroid.ecommerce.domain.model.Order
import com.chrisroid.ecommerce.features.auth.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
) : ViewModel() {

    private val _orderState = mutableStateOf<Resource<Order>?>(null)
    val orderState: State<Resource<Order>?> = _orderState

//    fun placeOrder(shippingAddress: String, paymentMethod: String) {
//        viewModelScope.launch {
//            val cartItems = cartViewModel.cartItems.value
//            val total = cartViewModel.getTotalPrice()
//
//            val order = Order(
//                userId = Firebase.auth.currentUser?.uid ?: "",
//                items = cartItems,
//                total = total,
//                shippingAddress = shippingAddress,
//                paymentMethod = paymentMethod
//            )
//
//            _orderState.value = repository.placeOrder(order)
//        }
//    }
}