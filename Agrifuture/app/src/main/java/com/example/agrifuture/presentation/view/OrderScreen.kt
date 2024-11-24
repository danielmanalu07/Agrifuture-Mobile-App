package com.example.agrifuture.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.order.OrderSection
import com.example.agrifuture.presentation.viewModel.OrderVM

@Composable
fun OrderScreen(orderVM: OrderVM, navController: NavController) {
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
                        Text("Payment", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                },
                backgroundColor = colorResource(id = R.color.green),
            )
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier.fillMaxWidth().padding(paddingValues).padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OrderSection(orderVM = orderVM, navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOrderScreen() {
    val navController = rememberNavController()
    val orderVM = OrderVM()
    OrderScreen(orderVM = orderVM, navController = navController)
}