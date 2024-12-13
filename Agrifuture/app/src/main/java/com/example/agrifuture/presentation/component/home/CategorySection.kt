package com.example.agrifuture.presentation.component.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Category
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.state.CategoryState
import com.example.agrifuture.presentation.viewModel.CategoryVM

@Composable
fun CategorySection(navController: NavController, categoryVM: CategoryVM) {
    val context = LocalContext.current

    val categoryState by categoryVM.category.collectAsState()

    LaunchedEffect(categoryState) {
        if (categoryState is CategoryState.Idle){
            categoryVM.getCategory()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Category",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        when(categoryState){
            is CategoryState.Idle -> {
                Text("No data available")
            }
            is CategoryState.Loading -> {}
            is CategoryState.Error -> {
                val errorMessage = (categoryState as CategoryState.Error).message
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red
                )
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            is CategoryState.Success -> {
                val categories = (categoryState as CategoryState.Success).kategori
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        CategoryItem(
                            category = category,
                            onClick = {
                                navController.navigate(Screen.DetailCategory.createRoute(category.id)){
                                    popUpTo(Screen.DetailCategory.route) {inclusive = true}
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
fun CategoryItem(
    category: Category,
    onClick: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ApiClient.BASE_URL_2 + "kategori/" + category.image_path,
            contentDescription = category.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategorySection() {
    val categoryVM = CategoryVM()
    val navController = rememberNavController()
    CategorySection(navController = navController, categoryVM = categoryVM)
}