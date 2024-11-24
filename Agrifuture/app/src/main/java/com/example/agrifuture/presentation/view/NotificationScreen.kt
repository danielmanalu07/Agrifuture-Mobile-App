package com.example.agrifuture.presentation.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.agrifuture.presentation.component.notification.NotificationItem
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.viewModel.NotificationVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    val notificationVM = NotificationVM()
    val notifications = notificationVM.getNotifications()
    val context = LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                backgroundColor = colorResource(id = R.color.white),
                actions = {
                    IconButton(
                        onClick = { Toast.makeText(context, "favorite", Toast.LENGTH_SHORT).show() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorite),
                            contentDescription = "favorite"
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.MyCart.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "cart"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BotomAppBarUI(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).offset(y = -30.dp)
        ) {
            item {
                Column (
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {

                }
                Box (
                    modifier = Modifier.background(
                        color = colorResource(id = R.color.green),
                        shape = MaterialTheme.shapes.small
                    ).height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Today",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.black),
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .fillMaxWidth()
                    )
                }
            }

            items(notifications) { notification ->
                NotificationItem(notification)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNotificatioScreen() {
    val navController = rememberNavController()
    NotificationScreen(navController = navController)
}