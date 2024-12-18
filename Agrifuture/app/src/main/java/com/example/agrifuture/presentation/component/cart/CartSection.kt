package com.example.agrifuture.presentation.component.cart

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Cart_Items
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.state.MyCartState
import com.example.agrifuture.presentation.state.OrderState
import com.example.agrifuture.presentation.state.UpdateQuantityState
import com.example.agrifuture.presentation.state.UpdateStatusState
import com.example.agrifuture.presentation.viewModel.CartVM
import com.example.agrifuture.presentation.viewModel.OrderVM

@Composable
fun CartSection(cartVM: CartVM, navController: NavController) {
    var totalItems by remember { mutableStateOf(0) }
    var selectAll by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val myCartState by cartVM.myCartState.collectAsState()
    val updateQuantityState by cartVM.updateQuantity.collectAsState()
    val updateStatusState by cartVM.updateStatus.collectAsState()
    val orderVM = OrderVM()
    val orderState by orderVM.orderState.collectAsState()
    val context = LocalContext.current

    // Handle state updates
    LaunchedEffect(Unit) {
        when (updateQuantityState) {
            is UpdateQuantityState.Success -> {
                cartVM.myCart(context)
            }
            is UpdateQuantityState.Error -> {
                // Handle error if needed
            }
            else -> {}
        }

        when(updateStatusState){
            is UpdateStatusState.Success -> {
                cartVM.myCart(context)
            }
            is UpdateStatusState.Error -> {
                // Handle error if needed
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        when(myCartState){
            is MyCartState.Idle -> {}
            is MyCartState.Loading -> {
                CircularProgressIndicator()
            }
            is MyCartState.Error -> {
                Text(text = (myCartState as MyCartState.Error).message, color = Color.Red)
            }
            is MyCartState.Success -> {
                val cartItems = (myCartState as MyCartState.Success).items
                cartItems.forEach { cartItem ->
                    CartItem(
                        cartItems = cartItem,
                        onQuantityChange = { newQuantity ->
                            cartItem.quantity = newQuantity
                            cartItem.Status = newQuantity > 0
                            cartVM.updateQuantity(cartItem.id, newQuantity, context)

                            if (newQuantity == 0) {
                                cartVM.myCart(context)
                            }

                            updateTotalItems(cartItems, onUpdate = { totalItems = it })
                        },
                        onCheckedChange = { isChecked ->
                            // Update cart item status via ViewModel
                            cartVM.updateStatus(cartItem.id, isChecked, context)

                            // Update local state for immediate UI feedback
                            cartItem.Status = isChecked
                            updateTotalItems(cartItems, onUpdate = { totalItems = it })

                            // Update select all state
                            selectAll = cartItems.all { it.Status }
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
                                cartItems.forEach {
                                    // Update status for each item via ViewModel
                                    cartVM.updateStatus(it.id, isChecked, context)
                                    it.Status = isChecked
                                }
                                updateTotalItems(cartItems, onUpdate = { totalItems = it })
                            }
                        )
                        Text("Semua")
                    }
                    Text("jumlah barang: $totalItems")
                    Button(
                        onClick = {
                            val hasSelectedItems = cartItems.any { it.Status }
                            if (hasSelectedItems) {
                                orderVM.addToOrder(context)
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
                        enabled = cartItems.any { it.Status }
                    ) {
                        Text("Check", color = colorResource(id = R.color.white))
                    }
                }
            }
        }
    }
}

fun updateTotalItems(cartItems: List<Cart_Items>, onUpdate: (Int) -> Unit) {
    val selectedItemsTotal = cartItems.filter { it.Status }.sumOf { it.quantity }
    onUpdate(selectedItemsTotal)
}

@Composable
fun CartItem(
    cartItems: Cart_Items,
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
            checked = cartItems.Status,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = ApiClient.BASE_URL_2 + "pupuk" + cartItems.fertilizer?.image_path,
            contentDescription = cartItems.fertilizer?.name,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = cartItems.fertilizer?.name ?: "Unknown Name", fontWeight = FontWeight.Bold)
            Text(text = "${cartItems.fertilizer?.stock} Kg")
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = "Rp. ${cartItems.fertilizer?.price}",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.gray)
                )

                Text(
                    text = cartItems.fertilizer?.seller?.store_name ?: "Unknown Seller",
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "-",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            val newQuantity = cartItems.quantity - 1
                            if (newQuantity >= 0) { // Mengizinkan kuantitas menjadi 0
                                onQuantityChange(newQuantity)
                            }
                        }
                )

                Text(
                    text = cartItems.quantity.toString(),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(
                    text = "+",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            val newQuantity = cartItems.quantity + 1
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
