package com.example.agrifuture.presentation.view

import android.media.Rating
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.viewModel.ProductVM

@Composable
fun ProductDetailScreen(navController: NavController, productId: Int) {
    val productVM = ProductVM()
    val product = productVM.getProducts().find { it.id == productId }
    val context = LocalContext.current

    product?.let {
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
                            Text("Details Product", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    },
                    backgroundColor = colorResource(id = R.color.green),
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = colorResource(id = R.color.background))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.white))
                        .height(700.dp)
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.white))
                            .padding(16.dp)
                    ) {
                        Row (
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                        ) {
                            Card(
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(
                                    contentColor = colorResource(id = R.color.white),
                                    containerColor = colorResource(id = R.color.white)
                                ),
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = it.image),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            Spacer(modifier = Modifier.width(50.dp))
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(it.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                                Spacer(modifier = Modifier.height(4.dp))

                                StarRating(it.rating, 5)

                                Spacer(modifier = Modifier.height(4.dp))

                                Text("Rp. ${it.price.toInt()}/kg", fontSize = 18.sp, color = colorResource(id = R.color.black))

                                Text(it.shop.name, fontSize = 14.sp, color = colorResource(id = R.color.gray))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Divider(modifier = Modifier.fillMaxWidth(), color = colorResource(id = R.color.black))

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Description", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(it.description, fontSize = 14.sp, modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp))

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green),
                                contentColor = colorResource(id = R.color.white)
                            )
                        ) {
                            Text("Beli Produk")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StarRating(rating: Int, maxRating: Int) {
    Row {
        for (i in 1..maxRating) {
            val starColor = if (i <= rating) colorResource(id = R.color.yellow) else Color.Gray
            Text(
                text = "★",
                fontSize = 30.sp,
                color = starColor
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductDetail() {
    val navController = rememberNavController()
    val productVM = ProductVM()
    val productList = productVM.getProducts()
    if (productList.isNotEmpty()) {
        ProductDetailScreen(navController = navController, productId = productList[0].id)
    } else {
        Text("No product available for preview")
    }
}