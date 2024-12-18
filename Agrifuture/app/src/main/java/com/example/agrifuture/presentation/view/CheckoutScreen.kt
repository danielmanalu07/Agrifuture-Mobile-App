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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.checkout.CheckoutSection
import com.example.agrifuture.presentation.model.Checkout
import com.example.agrifuture.presentation.model.Order_Items
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.state.MyOrderState
import com.example.agrifuture.presentation.viewModel.CheckoutVM
import com.example.agrifuture.presentation.viewModel.OrderVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckoutScreen(orderVM: OrderVM, navController: NavController) {

    val context = LocalContext.current
    val orderItems by orderVM.myorder.collectAsState()

    LaunchedEffect(orderItems) {
        if (orderItems is MyOrderState.Idle){
            orderVM.myOrder(context)
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }


    fun refresh() = refreshScope.launch {
        refreshing = true
        orderVM.apply {
            myOrder(context)
        }
        delay(1000)
        refreshing = false
    }

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
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when(orderItems) {
                is MyOrderState.Idle -> {}
                is MyOrderState.Loading -> {
                    CircularProgressIndicator()
                }
                is MyOrderState.Error -> {
                    val message = (orderItems as MyOrderState.Error).messsage
                    Text(text = message, color = Color.Red)
                }
                is MyOrderState.Success -> {
                    val items = (orderItems as MyOrderState.Success).items
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(refreshing),
                        onRefresh = {refresh()}
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 24.dp, start = 14.dp, end = 14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            item {
                                CheckoutSection(orderVM = orderVM, navController = navController)
                            }

                            item {
                                PaymentSummary(orderItems = items)
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        navController.navigate(Screen.Order.route) {
                                            popUpTo(Screen.Checkout.route) { inclusive = true }
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
        }
    }
}

@Composable
fun PaymentSummary(orderItems: List<Order_Items>) {
    val subtotal = orderItems.sumOf { (it.price).toDouble() * (it.quantity).toDouble() }
//    val shippingCost = 2500
    val total = subtotal

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
//    val checkoutVM = CheckoutVM()
    val orderVM = OrderVM()
    CheckoutScreen(orderVM = orderVM, navController = navController)
}