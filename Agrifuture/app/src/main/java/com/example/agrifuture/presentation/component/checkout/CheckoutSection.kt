package com.example.agrifuture.presentation.component.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Checkout
import com.example.agrifuture.presentation.viewModel.CheckoutVM

@Composable
fun CheckoutSection(checkoutVM: CheckoutVM, navController: NavController) {
    val checkouts = checkoutVM.getCheckouts()

    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        checkouts.forEach{ checkout ->
            CheckoutItem(checkout = checkout)
        }

    }
}

@Composable
fun CheckoutItem(checkout: Checkout) {
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
                Image(
                    painter = painterResource(id = checkout.cart.product.image),
                    contentDescription = null,
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
                        text = checkout.cart.product.name,
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
                        text = "${checkout.cart.product.stock} Kg",
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
                        text = "Rp. ${checkout.cart.product.price.toInt()}",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "x${checkout.cart.quantity}",
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
    val checkoutVM = CheckoutVM()

    CheckoutSection(checkoutVM = checkoutVM, navController = navController)
}