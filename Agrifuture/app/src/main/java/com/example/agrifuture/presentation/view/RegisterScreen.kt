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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.R
import com.example.agrifuture.presentation.component.authentication.ButtonUI
import com.example.agrifuture.presentation.component.authentication.TextFieldUI
import com.example.agrifuture.presentation.data.dto.register.RegisterRequest
import com.example.agrifuture.presentation.data.dto.register.RegisterResponse
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.service.AuthService
import com.example.agrifuture.presentation.state.AuthState
import com.example.agrifuture.presentation.state.LoginStates
import com.example.agrifuture.presentation.viewModel.AuthVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    authVM: AuthVM
) {
    val context = LocalContext.current
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        authVM.apply {
            onNameChange("")
            onEmailChange("")
            onPhoneChange("")
            onAddressChange("")
            onPasswordChange("")
            onPasswordConfirmChange("")
            nameError = null
            emailError = null
            phoneError = null
            addressError = null
            passwordError = null
            passwordConfirmError = null
        }
        delay(1000) // Simulate some delay
        refreshing = false
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(refreshing),
            onRefresh = { refresh() },
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

                    Spacer(modifier = Modifier.size(10.dp))

                    when(val state = authVM.authenticationState) {
                        is AuthState.Idle -> {}
                        is AuthState.Loading -> {}
                        is AuthState.Success -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }

                        is AuthState.Error -> {
                            Text(
                                state.message,
                                color = Color.Red,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }

                    TextFieldUI(
                        value = authVM.name,
                        onValueChange = {
                            authVM.onNameChange(it)
                            authVM.validateName()
                        },
                        label = "Enter Your Name",
                        leadingIconId = R.drawable.ic_person,
                        keyboardType = KeyboardType.Text,
                        errorMessage = authVM.nameError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = authVM.email,
                        onValueChange = {
                            authVM.onEmailChange(it)
                            authVM.validateEmail()
                        },
                        label = "Enter Your Email",
                        leadingIconId = R.drawable.ic_email,
                        keyboardType = KeyboardType.Email,
                        errorMessage = authVM.emailError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = authVM.phone,
                        onValueChange = {
                            authVM.onPhoneChange(it)
                            authVM.validatePhone()
                        },
                        label = "Enter Your Phone Number",
                        leadingIconId = R.drawable.ic_phone,
                        keyboardType = KeyboardType.Phone,
                        errorMessage = authVM.phoneError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = authVM.address,
                        onValueChange = {
                            authVM.onAddressChange(it)
                            authVM.validateAddress()
                        },
                        label = "Enter Your Address",
                        leadingIconId = R.drawable.ic_location,
                        keyboardType = KeyboardType.Text,
                        errorMessage = authVM.addressError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = authVM.password,
                        onValueChange = {
                            authVM.onPasswordChange(it)
                            authVM.validatePassword()
                        },
                        label = "Enter Your Password",
                        leadingIconId = R.drawable.ic_lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        isPasswordVisible = authVM.isPasswordVisible,
                        onPasswordVisibilityToggle = authVM::togglePasswordVisibility,
                        errorMessage = authVM.passwordError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    TextFieldUI(
                        value = authVM.confirm_password,
                        onValueChange = {
                            authVM.onPasswordConfirmChange(it)
                            authVM.validatePasswordConfirmation()
                        },
                        label = "Enter Confirmation Password",
                        leadingIconId = R.drawable.ic_lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        isPasswordVisible = authVM.isPasswordConfirmationVisible,
                        onPasswordVisibilityToggle = authVM::togglePasswordConfirmationVisibility,
                        errorMessage = authVM.passwordConfirmError
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        color = Color.Gray,
                        thickness = DividerDefaults.Thickness,
                    )

                    Spacer(modifier = Modifier.size(20.dp))

                    if (authVM.authenticationState is AuthState.Loading) {
                        ButtonUI(text = "Loading...") {}
                    } else {
                        ButtonUI(text = "Register") {
                            if (authVM.validateFields()) {
                                authVM.register(navController)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account? ",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Login Here",
                            color = colorResource(R.color.green),
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Login.route)
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
private fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    val authRepository = AuthRepository()
    val authVM = AuthVM(authRepository, listOf(), navController)
    RegisterScreen(navController = navController, authVM = authVM)

}