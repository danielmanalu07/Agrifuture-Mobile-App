package com.example.agrifuture.presentation.component.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Cart
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.CartVM

@Composable
fun CartSection(cartVM: CartVM, navController: NavController) {
    val cartItems = remember { cartVM.getCarts().toMutableList() }

    var totalItems by remember { mutableStateOf(0) }
    var selectAll by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        cartItems.forEach { cartItem ->
            CartItem(
                cart = cartItem,
                onQuantityChange = { newQuantity ->
                    cartItem.quantity = newQuantity
                    if (newQuantity > 0) {
                        cartItem.status = true
                    }
                    updateTotalItems(cartItems, onUpdate = { totalItems = it })
                },
                onCheckedChange = { isChecked ->
                    cartItem.status = isChecked
                    updateTotalItems(cartItems, onUpdate = { totalItems = it })
                    selectAll = cartItems.all { it.status }
                }
            )
        }

        if (showError) {
            Text(
                text = "Pilih minimal satu item untuk checkout",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectAll,
                    onCheckedChange = { isChecked ->
                        selectAll = isChecked
                        cartItems.forEach { it.status = isChecked }
                        updateTotalItems(cartItems, onUpdate = { totalItems = it })
                    }
                )
                Text("Semua")
            }
            Text("jumlah barang: $totalItems")
            Button(
                onClick = {
                    val hasSelectedItems = cartItems.any { it.status }
                    if (hasSelectedItems) {
                        navController.navigate(Screen.Checkout.route) {
                            popUpTo(Screen.MyCart.route) { inclusive = true }
                        }
                    } else {
                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    disabledContainerColor = colorResource(id = R.color.gray)
                ),
                enabled = cartItems.any { it.status }
            ) {
                Text("Check", color = colorResource(id = R.color.white))
            }
        }
    }
}

fun updateTotalItems(cartItems: List<Cart>, onUpdate: (Int) -> Unit) {
    val selectedItemsTotal = cartItems.filter { it.status }.sumOf { it.quantity }
    onUpdate(selectedItemsTotal)
}

@Composable
fun CartItem(
    cart: Cart,
    onQuantityChange: (Int) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = cart.status,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = cart.product.image),
            contentDescription = cart.product.name,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = cart.product.name, fontWeight = FontWeight.Bold)
            Text(text = "${cart.product.stock} Kg")
            Text(text = "Rp. ${cart.product.price}", fontSize = 14.sp, color = colorResource(id = R.color.gray))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "-",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            if (cart.quantity > 1) {
                                val newQuantity = cart.quantity - 1
                                onQuantityChange(newQuantity)
                            }
                        }

                )


                Text(
                    text = cart.quantity.toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    text = "+",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            val newQuantity = cart.quantity + 1
                            onQuantityChange(newQuantity)
                        }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCartSection() {
    val cartVM = CartVM()
    val navController = rememberNavController()
    CartSection(cartVM = cartVM, navController = navController)
}
