package com.example.agrifuture.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.presentation.component.botombar.BotomAppBarUI
import com.example.agrifuture.presentation.component.home.BannerSection
import com.example.agrifuture.presentation.component.home.CategorySection
import com.example.agrifuture.presentation.component.home.ProductSection
import com.example.agrifuture.presentation.component.topbar.TopAppBarUI
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.repository.BannerRepository
import com.example.agrifuture.presentation.repository.PupukRepository
import com.example.agrifuture.presentation.state.ProfileState
import com.example.agrifuture.presentation.viewModel.AuthVM
import com.example.agrifuture.presentation.viewModel.CategoryVM
import com.example.agrifuture.presentation.viewModel.ProductVM
import com.example.agrifuture.presentation.viewModel.PupukVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, categoryVM: CategoryVM) {
    val bannerRepository = BannerRepository()
    val authRepository = AuthRepository()
    val authVM = AuthVM(authRepository, listOf(), navController)

    val pupukRepository = PupukRepository()
    val pupukVM = PupukVM(pupukRepository)

    val context = LocalContext.current

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }


    fun refresh() = refreshScope.launch {
        refreshing = true
        authVM.apply {
            profile(context = context, navController = navController)
        }
        bannerRepository.apply {
             banners
        }
        categoryVM.apply {
            getCategory()
        }
        pupukVM.apply {
            fetchPupuk()
        }
        delay(1000)
        refreshing = false
    }

    Scaffold (
        topBar = {
            TopAppBarUI(navController = navController, authVM = authVM)
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
            SwipeRefresh(
                state = rememberSwipeRefreshState(refreshing),
                onRefresh = { refresh() }
            ) {
                LazyColumn {
                    item {
                        BannerSection(banners = bannerRepository.banners)
                    }
                    item{
                        CategorySection(navController = navController, categoryVM = categoryVM)
                    }
                    item{
                        ProductSection(pupukVM = pupukVM, navController = navController)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    val navController = rememberNavController()
    val categoryVM = CategoryVM()
    HomeScreen(navController = navController, categoryVM)
}