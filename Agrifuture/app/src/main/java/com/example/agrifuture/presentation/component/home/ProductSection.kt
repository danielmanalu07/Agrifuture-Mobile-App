package com.example.agrifuture.presentation.component.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.ProductVM

@Composable
fun ProductSection(productVM: ProductVM, navController: NavController) {
    val products = productVM.getProducts()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val columns = when {
        screenWidth < 600 -> 2
        screenWidth < 1200 -> 3
        else -> 4
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        products.chunked(columns).forEach { productsRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                productsRow.forEach { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(Screen.DetailProduct.createRoute(productId = product.id)){
                                popUpTo(Screen.DetailProduct.route) {inclusive = true}
                            }
                        },
                        navController = navController
                    )
                }
                if (productsRow.size < columns) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )

            Text(
                text = "${product.stock} kg",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )


            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rp. ${product.price.toInt()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = product.shop.name,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.MyCart.route) {
                        popUpTo(Screen.Home.route) {inclusive = true}
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8BC34A)
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Beli Produk",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductSection() {
    val productVM = ProductVM()
    val navController = rememberNavController()
    ProductSection(productVM = productVM, navController = navController)
}