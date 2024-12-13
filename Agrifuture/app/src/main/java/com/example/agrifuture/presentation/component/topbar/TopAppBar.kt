package com.example.agrifuture.presentation.component.topbar

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.state.ProfileState
import com.example.agrifuture.presentation.viewModel.AuthVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUI(navController: NavController, authVM: AuthVM) {
    val context = LocalContext.current
    val profileState by authVM.profileState.collectAsState()

    LaunchedEffect (profileState) {
        if (profileState is ProfileState.Idle) {
            authVM.profile(context = context, navController = navController)
        }
    }
    Surface(
        shape = RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp),
        shadowElevation = 5.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            title = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    (profileState as? ProfileState.Success)?.let { data ->
                        Text("Hello, ${data.data.name}", fontSize = 14.sp, modifier = Modifier.height(20.dp))
                        Text(data.data.address, fontSize = 8.sp, modifier = Modifier.height(20.dp))
                    }
                }
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.user_logo),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 5.dp).size(35.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.background),
                titleContentColor = colorResource(id = R.color.black),
                navigationIconContentColor = colorResource(id = R.color.black)
            ),
            actions = {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "favorite", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "favorite"
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(28.dp)
                        .width(1.dp),
                    color = Color.Black,
                    thickness = 2.dp
                )

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
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTopAppBar() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authVM = AuthVM(authRepository, listOf(), navController)
    TopAppBarUI(navController = navController, authVM = authVM)
}