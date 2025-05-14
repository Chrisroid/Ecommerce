package com.chrisroid.ecommerce.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.chrisroid.ecommerce.features.cart.CartViewModel
import com.chrisroid.ecommerce.navigation.ECommerceApp
import com.chrisroid.ecommerce.ui.theme.ECommerceTheme
import com.theelitedevelopers.paystackwebview.data.constants.PayStackWebViewConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECommerceTheme {
                ECommerceApp()
            }
        }
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PayStackWebViewConstants.REQUEST_CODE && data != null) {
            if (requestCode == PayStackWebViewConstants.RESULT_SUCCESS) {
                val reference = data.getStringExtra(PayStackWebViewConstants.REFERENCE)
                val accessCode = data.getStringExtra(PayStackWebViewConstants.ACCESS_CODE)
                viewModel.setPaymentSuccess(reference!!)
            } else if (requestCode == PayStackWebViewConstants.RESULT_CANCELLED) {
                viewModel.setPaymentFailed("Payment was cancelled")
            } else {
                viewModel.setPaymentFailed("Payment failed")
            }
        }
    }
}