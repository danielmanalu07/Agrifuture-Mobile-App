package com.example.agrifuture.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.agrifuture.presentation.component.botombar.BotomAppBarUI
import com.example.agrifuture.presentation.component.shop.ShopSection
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.ProductVM

@Composable
fun ShopScreen(navController: NavController) {
    val productVM = ProductVM()
    val context = LocalContext.current
    val products = productVM.getProducts()
    val uniqueShops = products.map { it.shop }.distinct()

    var searchQuery by remember { mutableStateOf("") }
    var showSearchField by remember { mutableStateOf(false) }

    val filteredProducts = if (searchQuery.isEmpty()) {
        products
    } else {
        products.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearchField) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search products") },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorResource(id = R.color.white)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Shop", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                backgroundColor = colorResource(id = R.color.white),
                actions = {
                    IconButton(
                        onClick = { showSearchField = !showSearchField }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search"
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.MyCart.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "Cart"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BotomAppBarUI(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(uniqueShops) { shop ->
                val shopProducts = filteredProducts.filter { it.shop.id == shop.id }
                if (shopProducts.isNotEmpty()) {
                    ShopSection(
                        shop = shop,
                        products = shopProducts,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewShopScreen() {
    val navController = rememberNavController()
    ShopScreen(navController = navController)
}