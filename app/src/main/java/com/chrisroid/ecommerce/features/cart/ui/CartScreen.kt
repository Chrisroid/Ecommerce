package com.chrisroid.ecommerce.features.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisroid.ecommerce.features.cart.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onCheckout: () -> Unit,
    onBack: () -> Unit
) {
    val cartItems by cartViewModel.cartItems
    val totalPrice = cartViewModel.getTotalPrice()
    var showCheckoutDialog by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        // You would get this from Paystack callback
        val paymentSuccess = false // Replace with actual payment status

        if (paymentSuccess) {
            cartViewModel.clearCart()
            onCheckout()
        }
    }

    if (showCheckoutDialog) {
        CheckoutDialog(
            cartItems = cartItems,
            totalPrice = totalPrice,
            onDismiss = { showCheckoutDialog = false },
            onPayWithPaystack = {
                // Initialize Paystack payment here
                showCheckoutDialog = false
//                initiatePaystackPayment(totalPrice, cartItems)
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total:",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            text = "₦${"%.2f".format(totalPrice)}",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { showCheckoutDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = cartItems.isNotEmpty()
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            EmptyCartScreen()
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(cartItems) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = {
                            cartViewModel.updateQuantity(item.productId, item.quantity + 1)
                        },
                        onDecrease = {
                            cartViewModel.updateQuantity(item.productId, item.quantity - 1)
                        },
                        onRemove = {
                            cartViewModel.removeFromCart(item.productId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyCartScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.ShoppingCart,
            contentDescription = "Empty Cart",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Browse products and add some items to your cart",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}