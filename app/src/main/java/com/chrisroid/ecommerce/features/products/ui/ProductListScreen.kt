package com.chrisroid.ecommerce.features.products.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisroid.ecommerce.features.cart.CartViewModel
import com.chrisroid.ecommerce.features.products.ProductViewModel
import com.chrisroid.ecommerce.ui.components.BadgedCartIcon
import com.chrisroid.ecommerce.ui.components.CenterLoading
import com.chrisroid.ecommerce.ui.components.ErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit
) {
    val products by viewModel.products
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val cartItemCount by cartViewModel.cartItems

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    IconButton(onClick = { viewModel.refreshProducts() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    BadgedCartIcon(
                        itemCount = cartItemCount.size, // Use cart items count
                        onClick = onCartClick,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading && products.isEmpty()) {
                CenterLoading()
            } else if (error != null && products.isEmpty()) {
                ErrorView(error = error, onRetry = { viewModel.loadProducts() })
            } else {
                LazyColumn {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }

            if (isLoading && products.isNotEmpty()) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}