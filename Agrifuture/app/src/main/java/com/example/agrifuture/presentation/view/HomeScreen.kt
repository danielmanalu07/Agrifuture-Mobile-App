package com.example.agrifuture.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.presentation.component.botombar.BotomAppBarUI
import com.example.agrifuture.presentation.component.home.BannerSection
import com.example.agrifuture.presentation.component.home.CategorySection
import com.example.agrifuture.presentation.component.home.ProductSection
import com.example.agrifuture.presentation.component.topbar.TopAppBarUI
import com.example.agrifuture.presentation.repository.BannerRepository
import com.example.agrifuture.presentation.viewModel.CategoryVM
import com.example.agrifuture.presentation.viewModel.ProductVM

@Composable
fun HomeScreen(navController: NavController) {
    val bannerRepository = BannerRepository()
    val categoryVM = CategoryVM()
    val productVM = ProductVM()
    Scaffold (
        topBar = {
            TopAppBarUI(navController = navController)
        },
        bottomBar = {
            BotomAppBarUI(navController = navController)
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn {
                item {
                    BannerSection(banners = bannerRepository.banners)
                }
                item{
                    CategorySection(navController = navController, categoryVM = categoryVM)
                }
                item{
                    ProductSection(productVM = productVM, navController = navController)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}