package com.example.agrifuture.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.CategoryVM
import com.example.agrifuture.presentation.viewModel.ProductVM

@Composable
fun CategoryDetailScreen(navController: NavController, categoryId: Int) {
    val categoryVM = CategoryVM()
    val productVM = ProductVM()
    val category = categoryVM.getCategories().find { it.id == categoryId }

    category?.let {
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
                            Text(it.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    },
                    backgroundColor = colorResource(id = R.color.green),
                )
            }
        ) { paddingValues ->
            if (productVM.getProductsForCategory(categoryId).isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 142.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(productVM.getProductsForCategory(categoryId)) { product ->
                        ProductCard(
                            navController = navController,
                            product = product,
                            onClick = {
                                navController.navigate(Screen.DetailProduct.createRoute(productId = product.id)){
                                    popUpTo(Screen.DetailProduct.route) {inclusive = true}
                                }
                            }
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Product Not Found",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    navController: NavController,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .width(142.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(166.dp)
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.black)
            )

            Text(
                text = "Rp ${product.price.toInt()}",
                fontSize = 12.sp,
                color = colorResource(id = R.color.black)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "★",
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.yellow)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${product.rating.toDouble()}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "10k+ terjual",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.shop.name,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        navController.navigate(Screen.MyCart.route){
                            popUpTo(Screen.DetailCategory.route) {inclusive = true}
                        }
                    },
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Add to cart",
                        modifier = Modifier.size(28.dp),
                        tint = colorResource(id = R.color.black)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoryDetailScreen() {
    val navController = rememberNavController()
    val categoryVM = CategoryVM()
    val categoryList = categoryVM.getCategories()
    if (categoryList.isNotEmpty()) {
        CategoryDetailScreen(navController = navController, categoryId = categoryList[0].id)
    } else {
        Text("No product available for preview")
    }
}