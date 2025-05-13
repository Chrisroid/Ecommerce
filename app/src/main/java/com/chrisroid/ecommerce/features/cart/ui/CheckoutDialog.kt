package com.chrisroid.ecommerce.features.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisroid.ecommerce.data.model.CartItem


@Composable
fun CheckoutDialog(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onDismiss: () -> Unit,
    onPayWithPaystack: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Order Summary") },
        text = {
            Column {
                LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                    items(cartItems) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${item.quantity}x ${item.name}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "₦${"%.2f".format(item.price * item.quantity)}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "₦${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onPayWithPaystack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pay with Paystack")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    )
}