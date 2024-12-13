package com.example.agrifuture.presentation.component.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.model.Pupuk
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.PupukRepository
import com.example.agrifuture.presentation.viewModel.ProductVM
import com.example.agrifuture.presentation.viewModel.PupukVM

@Composable
fun ProductSection(navController: NavController, pupukVM: PupukVM) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val pupuks by pupukVM.pupukList.collectAsState()

    LaunchedEffect(Unit) {
        pupukVM.fetchPupuk()
    }


    val columns = when {
        screenWidth < 600 -> 2
        screenWidth < 1200 -> 3
        else -> 4
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        pupuks.chunked(columns).forEach { productsRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                productsRow.forEach { product ->
                    ProductCard(
                        pupuk = product,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            navController.navigate(Screen.DetailProduct.createRoute(id = product.id)) {
                                popUpTo(Screen.DetailProduct.route) { inclusive = true }
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
    pupuk: Pupuk,
    onClick: () -> Unit,
    navController: NavController
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = ApiClient.BASE_URL_2 + "pupuk" + pupuk.image_path,
                contentDescription = pupuk.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pupuk.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Stock: ${pupuk.stock} kg",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rp. ${pupuk.price}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = pupuk.sellers?.store_name ?: "Unknown Seller",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A))
            ) {
                Text(
                    text = "Beli Produk",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewProductSection() {
//    val productVM = ProductVM()
    val navController = rememberNavController()
    val pupukRepository = PupukRepository()
    val pupukVM = PupukVM(pupukRepository)
    ProductSection(pupukVM = pupukVM, navController = navController)
}