package com.chrisroid.ecommerce.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chrisroid.ecommerce.features.auth.AuthService
import com.chrisroid.ecommerce.features.auth.AuthViewModel
import com.chrisroid.ecommerce.features.auth.ui.AuthScreen
import com.chrisroid.ecommerce.features.cart.CartViewModel
import com.chrisroid.ecommerce.features.cart.ui.CartScreen
import com.chrisroid.ecommerce.features.products.ProductViewModel
import com.chrisroid.ecommerce.features.products.ui.ProductDetailScreen
import com.chrisroid.ecommerce.features.products.ui.ProductListScreen

@Composable
fun ECommerceApp(
    authViewModel: AuthViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val authService = remember { AuthService() }
    val navController = rememberNavController()

    val startDestination = if (authService.getCurrentUser() != null) "products" else "auth"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("auth") {
            AuthScreen(
                viewModel = authViewModel,
                onLoginSuccess = { navController.navigate("products") { popUpTo("auth") } }
            )
        }

        composable("products") {
            ProductListScreen(
                viewModel = productViewModel,
                onProductClick = { productId ->
                    navController.navigate("product/$productId")
                },
                onCartClick = {
                    navController.navigate("cart")
                }
            )
        }

        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                productRepository = productViewModel.repository,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                onCheckout = {
                    cartViewModel.clearCart()
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}