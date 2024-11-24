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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.authentication.TextFieldUI
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.state.LoginStates
import com.example.agrifuture.presentation.viewModel.AuthVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    loginVM: AuthVM = viewModel(),
) {
    val context = LocalContext.current
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        loginVM.apply {
            onEmailChange("")
            onPasswordChange("")
            emailError = null
            passwordError = null
        }
        delay(1000)
        refreshing = false
    }
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { refresh() }
        ){
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(R.drawable.ic_splash_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(200.dp)
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    when (val state = loginVM.loginState) {
                        is LoginStates.Idle -> {
                        }

                        is LoginStates.Loading -> {}

                        is LoginStates.Success -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }

                        is LoginStates.Error -> {
                            val errorMessage = (state as LoginStates.Error).message
                            Text(
                                errorMessage,
                                color = Color.Red,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    TextFieldUI(
                        value = loginVM.email,
                        onValueChange = {
                            loginVM.onEmailChange(it)
                            loginVM.validateEmailLogin()
                        },
                        label = "Enter Your Email",
                        leadingIconId = R.drawable.ic_email,
                        keyboardType = KeyboardType.Email,
                        errorMessage = loginVM.emailError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = loginVM.password,
                        onValueChange = {
                            loginVM.onPasswordChange(it)
                            loginVM.validatePassword()
                        },
                        label = "Enter Your Password",
                        leadingIconId = R.drawable.ic_lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        isPasswordVisible = loginVM.isPasswordVisible,
                        onPasswordVisibilityToggle = loginVM::togglePasswordVisibility,
                        errorMessage = loginVM.passwordError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        color = Color.Gray,
                        thickness = DividerDefaults.Thickness,
                    )

                    Spacer(modifier = Modifier.size(40.dp))


                    if (loginVM.loginState is LoginStates.Loading) {
                        ButtonUI(
                            text = "Loading...",
                        ) {}
                    } else {
                        ButtonUI(
                            text = "Login",
                            onClick = {
                                if (loginVM.validateFieldsLogin()) {
                                    loginVM.login(navController = navController, context)
                                }
                            }
                        )
                    }


                    Spacer(modifier = Modifier.size(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have any account? ",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Register Here",
                            color = colorResource(R.color.green),
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Register.route)
                            },
                            fontSize = 14.sp
                        )
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authVM = AuthVM(authRepository, listOf(), navController)
    LoginScreen(loginVM = authVM, navController = navController)
}