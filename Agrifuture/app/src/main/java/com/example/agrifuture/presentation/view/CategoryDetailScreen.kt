package com.example.agrifuture.presentation.view

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.AsyncImage
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.model.Pupuk
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.PupukRepository
import com.example.agrifuture.presentation.state.CategoryState
import com.example.agrifuture.presentation.viewModel.CategoryVM
import com.example.agrifuture.presentation.viewModel.ProductVM
import com.example.agrifuture.presentation.viewModel.PupukVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategoryDetailScreen(navController: NavController, id: Int, categoryVM: CategoryVM, pupukVM: PupukVM) {
    val context = LocalContext.current
    val pupukByCategory by pupukVM.pupukByCategory.collectAsState()
    val categoryState by categoryVM.category.collectAsState()

    LaunchedEffect(pupukByCategory) {
        if (categoryState is CategoryState.Idle) {
            categoryVM.getById(id)
        }
        pupukVM.getPupukByCategory(id)
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        pupukVM.apply {
            getPupukByCategory(id)
        }
        delay(1000)
        refreshing = false
    }


    Log.d("dataPupuk", pupukByCategory.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        when (categoryState) {
                            is CategoryState.Idle -> Text("No data available")
                            is CategoryState.Loading -> CircularProgressIndicator()
                            is CategoryState.Error -> {
                                val errorMessage = (categoryState as CategoryState.Error).message
                                Text("Error: $errorMessage", color = Color.Red)
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            is CategoryState.Success -> {
                                val categories = (categoryState as CategoryState.Success).kategori
                                val category = categories.find { it.id == id }
                                Text(category?.name ?: "Category not found", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                },
                backgroundColor = colorResource(id = R.color.green),
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { refresh()}
        ) {
            if (pupukByCategory.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Pupuk available for this category", fontSize = 16.sp, color = Color.Gray)
                }
            } else {
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
                    items(pupukByCategory) { pupuk ->
                        ProductCard(
                            navController = navController,
                            pupuk = pupuk,
                            onClick = {
                                navController.navigate(Screen.DetailProduct.createRoute(id = pupuk.id)) {
                                    popUpTo(Screen.DetailProduct.route) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    pupuk: Pupuk,
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
                AsyncImage(
                    model = ApiClient.BASE_URL_2 + "pupuk" + pupuk.image_path,
                    contentDescription = pupuk.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pupuk.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.black)
            )

            Text(
                text = "Rp ${pupuk.price}",
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
                    text = "5.0",
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
                    text = pupuk.seller?.store_name ?: "Unknown Seller",
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
    val pupukRepository = PupukRepository()
    val pupukVM = PupukVM(pupukRepository)
    CategoryDetailScreen(navController = navController, id = 1, categoryVM = categoryVM, pupukVM)

}