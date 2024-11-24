package com.example.agrifuture.presentation.view

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.checkout.CheckoutSection
import com.example.agrifuture.presentation.model.Checkout
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.CheckoutVM

@Composable
fun CheckoutScreen(checkoutVM: CheckoutVM, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text("Order Summary", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                backgroundColor = colorResource(id = R.color.green),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxSize().padding(bottom = 24.dp, start = 14.dp, end = 14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                item{
                    CheckoutSection(checkoutVM = checkoutVM, navController = navController)
                }

                item{
                    PaymentSummary(checkouts = checkoutVM.getCheckouts())
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate(Screen.Order.route){
                                popUpTo(Screen.Checkout.route){inclusive  = true}
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green),
                        ),
                    ) {
                        Text("Checkout", color = colorResource(id = R.color.white))
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentSummary(checkouts: List<Checkout>) {
    val subtotal = checkouts.sumOf { it.cart.product.price.toInt() * it.cart.quantity }
    val shippingCost = checkouts[0].ongkir
    val total = subtotal + shippingCost

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        )
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal untuk produk",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "Rp. ${subtotal.toInt()}",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.black)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Biaya Pengiriman",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "Rp. ${shippingCost.toInt()}",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.black)
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Pembayaran",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "Rp. ${total.toInt()}",
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black)
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCheckoutScreen() {
    val navController = rememberNavController()
    val checkoutVM = CheckoutVM()
    CheckoutScreen(checkoutVM = checkoutVM, navController = navController)
}