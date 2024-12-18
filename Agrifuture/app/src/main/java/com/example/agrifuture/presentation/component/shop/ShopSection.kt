package com.example.agrifuture.presentation.component.shop

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Pupuk
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.PupukVM

@Composable
fun ShopSection(
    pupuk: List<Pupuk>,
    navController: NavController,
    pupukVM: PupukVM
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(2.dp)
    ) {
        GreenLabel(pupuk.firstOrNull()?.seller?.store_name ?: "Unknown Seller")
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(pupuk) { product ->
                ProductCard(
                    pupuk = product,
                    onClick = {
                        navController.navigate(Screen.DetailProduct.createRoute(product.id))
                    }
                )
            }
        }
    }
}

@Composable
fun GreenLabel(text: String) {
    val greenColor = colorResource(id = R.color.green)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(IntrinsicSize.Max)
            .height(40.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width - 20.dp.toPx(), 0f)
                lineTo(size.width, size.height / 2)
                lineTo(size.width - 20.dp.toPx(), size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path, color = greenColor)
        }
        Text(
            text = text,
            modifier = Modifier.padding(start = 12.dp, end = 28.dp),
            color = colorResource(id = R.color.white),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    pupuk: Pupuk,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(200.dp)
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
            AsyncImage(
                model = ApiClient.BASE_URL_2 + "pupuk" + pupuk.image_path,
                contentDescription = pupuk.name,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pupuk.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )


            Text(
                text = "${pupuk.stock} kg",
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
                    text = "Rp. ${pupuk.price}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = pupuk.seller?.store_name ?: "Unknown Seller",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

