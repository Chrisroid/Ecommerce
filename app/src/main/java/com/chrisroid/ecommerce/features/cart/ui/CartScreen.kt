package com.chrisroid.ecommerce.features.cart.ui

import android.app.Activity
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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chrisroid.ecommerce.features.cart.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.theelitedevelopers.paystackwebview.PayStackWebViewForAndroid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    navController: NavController
) {
    val cartItems by cartViewModel.cartItems
    val totalPrice = cartViewModel.getTotalPrice()
    var showCheckoutDialog by rememberSaveable { mutableStateOf(false) }
    val paymentStatus by cartViewModel.paymentStatus.observeAsState()

    // Handle payment status changes
    LaunchedEffect(paymentStatus) {
        when (paymentStatus) {
            is CartViewModel.PaymentStatus.SUCCESS -> {
                cartViewModel.clearCart()
                navController.navigate("home") {
                    popUpTo("cart") { inclusive = true }
                }
            }

            else -> {}
        }
    }

    // Payment Success Dialog
    if (paymentStatus is CartViewModel.PaymentStatus.SUCCESS) {
        AlertDialog(
            onDismissRequest = { cartViewModel.resetPaymentStatus() },
            title = { Text("Payment Successful") },
            text = {
                Text("Your payment was processed successfully. Order reference: ${(paymentStatus as CartViewModel.PaymentStatus.SUCCESS).reference}")
            },
            confirmButton = {
                Button(
                    onClick = {
                        cartViewModel.resetPaymentStatus()
                        navController.navigate("home") {
                            popUpTo("cart") { inclusive = true }
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    // Checkout Dialog
    if (showCheckoutDialog) {
        val context = LocalContext.current
        val activity = context as? Activity ?: return

        CheckoutDialog(
            cartItems = cartItems,
            totalPrice = totalPrice,
            onDismiss = { showCheckoutDialog = false },
            onPayWithPaystack = {
                showCheckoutDialog = false
                cartViewModel.setPaymentProcessing()
                initiatePayment(
                    context = activity,
                    amount = totalPrice * 100,
                    email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                    secretKey = "sk_test_7e668b8b1875757894ad6bc7f28c8e2557603c82",
                    callbackUrl = "www.google.com",
                    progress = true
                )
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

fun initiatePayment(
    context: Activity,
    amount: Double,
    email: String,
    secretKey: String,
    callbackUrl: String,
    progress: Boolean
) {
    PayStackWebViewForAndroid(context)
        .setAmount(amount)
        .setEmail(email)
        .setSecretKey(secretKey)
        .setCallbackURL(callbackUrl)
        .showProgressBar(progress)
        .initialize()
}