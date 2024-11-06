package com.example.agrifuture.presentation.component.botombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.navigation.Screen

@Composable
fun BotomAppBarUI(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

    Surface(
        shadowElevation = 5.dp
    ) {
        BottomAppBar(
            containerColor = Color.White,
            modifier = Modifier.height(80.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //Home
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick ={
                            navController.navigate(Screen.Home.route){
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_home),
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp),
                            tint =if (currentRoute == Screen.Home.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black)
                        )
                    }
                    Text(
                        text = "Home",
                        color = if (currentRoute == Screen.Home.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black),
                        fontSize = 12.sp
                    )
                }

                //Shop
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Shop.route){
                                popUpTo(Screen.Shop.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_shopbag),
                            contentDescription = "Shop",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == Screen.Shop.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black)
                        )
                    }
                    Text(
                        text = "Shop",
                        color = if (currentRoute == Screen.Shop.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black),
                        fontSize = 12.sp
                    )
                }

                //Recommended
                Box(
                    modifier = Modifier.weight(1f).offset(y = (-14).dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.Recommendation.route){
                                popUpTo(Screen.Recommendation.route) { inclusive = true }
                            }
                        },
                        containerColor = colorResource(id = R.color.green),
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_recommended),
                            contentDescription = "Recommendation",
                            modifier = Modifier.size(45.dp),
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }

                //Notification
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Notification.route){
                                popUpTo(Screen.Notification.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_notification),
                            contentDescription = "Notification",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == Screen.Notification.route) colorResource(R.color.dark_green) else colorResource(id = R.color.black)
                        )
                    }
                    Text(
                        text = "Notification",
                        color = if (currentRoute == Screen.Notification.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black),
                        fontSize = 12.sp
                    )
                }

                //Profile
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Profile.route){
                                popUpTo(Screen.Profile.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.nav_profile),
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == Screen.Profile.route) colorResource(R.color.dark_green) else colorResource(id = R.color.black)
                        )
                    }
                    Text(
                        text = "Profile",
                        color = if (currentRoute == Screen.Profile.route) colorResource(id = R.color.dark_green) else colorResource(id = R.color.black),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomAppBar() {
    val navController = rememberNavController()
    BotomAppBarUI(navController = navController)
}