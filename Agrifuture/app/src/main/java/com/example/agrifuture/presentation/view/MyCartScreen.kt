package com.example.agrifuture.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.cart.CartSection
import com.example.agrifuture.presentation.state.MyCartState
import com.example.agrifuture.presentation.viewModel.CartVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MyCartScreen(navController: NavController, cartVM: CartVM) {
    val myCartState by cartVM.myCartState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect (myCartState) {
        if (myCartState is MyCartState.Idle) {
            cartVM.myCart(context)
        }
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }


    fun refresh() = refreshScope.launch {
        refreshing = true
        cartVM.apply {
            myCart(context)
        }
        delay(1000)
        refreshing = false
    }
    Scaffold (
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
                        Text("My Cart", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                backgroundColor = colorResource(id = R.color.green),
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = {refresh()}
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
            ) {
                item {
                    CartSection(cartVM = cartVM, navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMyCartScreen() {
    val navController = rememberNavController()
    val cartVM = CartVM()
    MyCartScreen(navController = navController, cartVM = cartVM)
}