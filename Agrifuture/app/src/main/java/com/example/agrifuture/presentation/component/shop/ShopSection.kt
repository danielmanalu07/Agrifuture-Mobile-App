package com.example.agrifuture.presentation.component.shop

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.model.Shop
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.ProductRepository
import com.example.agrifuture.presentation.repository.ShopRepository

@Composable
fun ShopSection(
    shop: Shop,
    products: List<Product>,
    navController: NavController
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(2.dp)
    ) {
        GreenLabel(shop.name)
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        navController.navigate(Screen.DetailProduct.createRoute(productId = product.id)){
                            popUpTo(Screen.DetailProduct.route) {inclusive = true}
                        }
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
    product: Product,
    onClick: () -> Unit
) {
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
        }
    }
}

