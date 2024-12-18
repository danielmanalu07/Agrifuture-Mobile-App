package com.example.agrifuture.presentation.component.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Order_Items
import com.example.agrifuture.presentation.state.MyOrderState
import com.example.agrifuture.presentation.viewModel.CheckoutVM
import com.example.agrifuture.presentation.viewModel.OrderVM

@Composable
fun CheckoutSection(orderVM: OrderVM, navController: NavController) {
    val myOrderState by orderVM.myorder.collectAsState()

    when (val state = myOrderState) {
        is MyOrderState.Success -> {
            state.items.forEach { item ->
                CheckoutItem(orderItems = item)
            }
        }
        is MyOrderState.Loading -> {
            CircularProgressIndicator()
        }
        is MyOrderState.Error -> {
            Text(text = state.messsage, color = Color.Red)
        }
        else -> Unit
    }
}


@Composable
fun CheckoutItem(orderItems: Order_Items) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.white)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Card(
                modifier = Modifier
                    .size(120.dp),
                elevation = CardDefaults.cardElevation(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white)
                ),
                shape = RoundedCornerShape(8.dp)
            )  {
                AsyncImage(
                    model = ApiClient.BASE_URL_2 + "pupuk" + orderItems.fertilizer?.image_path,
                    contentDescription = orderItems.fertilizer?.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Column  (
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = orderItems.fertilizer?.name ?: "Unknown Name",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "-",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${orderItems.fertilizer?.stock} Kg",
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black)
                    )
                }

                Spacer(modifier = Modifier.size(50.dp))

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Rp. ${orderItems.fertilizer?.price}",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "x${orderItems.quantity}",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier.offset(x = 25.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCheckoutSection() {
    val navController = rememberNavController()
//    val checkoutVM = CheckoutVM()
    val orderVM = OrderVM()
    CheckoutSection(orderVM = orderVM, navController = navController)
}