package com.example.agrifuture.presentation.view

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
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.viewModel.LoginState
import com.example.agrifuture.presentation.viewModel.LoginVM

@Composable
fun LoginScreen(
    navController: NavController,
    loginVM: LoginVM = viewModel(),
) {
    val emailError = loginVM.emailError
    val passwordError = loginVM.passwordError
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
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

                when (loginVM.loginState) {
                    is LoginState.Idle -> {
                    }
                    is LoginState.Success -> {
                        Text("Login Successful!", color = Color.Green)
                    }
                    is LoginState.Error -> {
                        Text((loginVM.loginState as LoginState.Error).message, color = Color.Red)
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                TextFieldUI(
                    value = loginVM.email,
                    onValueChange ={
                        loginVM.onEmailChange(it)
                        loginVM.validateEmail()
                    },
                    label = "Enter Your Email",
                    leadingIconId = R.drawable.ic_email,
                    keyboardType = KeyboardType.Email,
                    errorMessage = emailError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = loginVM.password,
                    onValueChange ={
                        loginVM.onPasswordChange(it)
                        loginVM.validatePassword()
                    },
                    label = "Enter Your Password",
                    leadingIconId = R.drawable.ic_lock,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    isPasswordVisible = loginVM.isPasswordVisible,
                    onPasswordVisibilityToggle = loginVM::togglePasswordVisibility,
                    errorMessage = passwordError
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


                if (loginVM.isLoading) {
                    ButtonUI(
                        text = "Loading...",
                    ) {}
                } else {
                    ButtonUI(
                        text = "Login",
                        onClick ={
                            if (loginVM.validateFields()){
                                loginVM.login(navController = navController)
                            } else {
                                null
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


@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    val navController = rememberNavController()
    val authRepo = CustomerRepository()
    val context = LocalContext.current
    val loginVM by lazy { LoginVM(authRepo, context) }
    LoginScreen(loginVM = loginVM, navController = navController)
}