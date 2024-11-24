package com.example.agrifuture.presentation.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.botombar.BotomAppBarUI
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.state.ProfileState
import com.example.agrifuture.presentation.viewModel.AuthVM

@Composable
fun ProfileScreen(navController: NavController, authVM: AuthVM) {
    val context = LocalContext.current
    val showLogoutDialog = remember { mutableStateOf(false) }

    val customer by authVM.customers.collectAsState()

    LaunchedEffect(Unit) {
        if (authVM.profileState !is ProfileState.Success) {
            authVM.profile(context)
        }
    }

    Scaffold(
        bottomBar = {
            BotomAppBarUI(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
                .padding(paddingValues),
        ) {
            // Header Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = colorResource(id = R.color.background))
                    .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)),
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                    )
                }

                Text(
                    text = "Profile",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                )
            }

            // Profile Image
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = -50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_logo),
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Edit Profile Photo",
                    tint = colorResource(id = R.color.black),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            Toast
                                .makeText(context, "Edit Profile Photo", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }

            when (val state = authVM.profileState) {
                is ProfileState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading profile...")
                    }
                }
                is ProfileState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ProfileState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -35.dp)
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.data.name,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ),
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(
                            text = state.data.email,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.gray)
                            ),
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(
                            text = state.data.phone,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.gray)
                            ),
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(
                            text = state.data.address,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.gray)
                            ),
                        )

                        Spacer(modifier = Modifier.size(20.dp))

                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
                is ProfileState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ItemProfile(R.drawable.ic_editprofile, "Edit Profile") {
                    Toast.makeText(context, "Edit Profile Screen", Toast.LENGTH_SHORT).show()
                }
                ItemProfile(R.drawable.ic_payment, "Payment") {
                    Toast.makeText(context, "Payment Screen", Toast.LENGTH_SHORT).show()
                }
                ItemProfile(R.drawable.ic_cart, "Cart") {
                    Toast.makeText(context, "Cart Screen", Toast.LENGTH_SHORT).show()
                }
                ItemProfile(R.drawable.ic_location, "Location") {
                    Toast.makeText(context, "Location Screen", Toast.LENGTH_SHORT).show()
                }
                ItemProfile(R.drawable.ic_favorite_2, "Favorite") {
                    Toast.makeText(context, "Favorite Screen", Toast.LENGTH_SHORT).show()
                }

                Spacer(modifier = Modifier.size(100.dp))

                ButtonUI(
                    text = "Logout",
                    onClick = {
                        showLogoutDialog.value = true
                    }
                )

                if (showLogoutDialog.value) {
                    LogoutDialog(
                        showDialog = showLogoutDialog,
                        onConfirm = {
                            authVM.logout(context)
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                            Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemProfile(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = colorResource(id = R.color.black),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, color = colorResource(id = R.color.black))
    }
}

@Composable
fun LogoutDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = "Logout Confirmation") },
        text = { Text(text = "Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Confirm Logout")
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authVM = AuthVM(authRepository, listOf(), navController)
    ProfileScreen(navController = navController, authVM)
}
