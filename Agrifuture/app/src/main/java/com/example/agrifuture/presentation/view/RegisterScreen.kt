package com.example.agrifuture.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.viewModel.RegisterState
import com.example.agrifuture.presentation.viewModel.RegisterVM

@Composable
fun RegisterScreen(
    navController: NavController,
    registerVM: RegisterVM
) {
    var nameError = registerVM.nameError
    var emailError =  registerVM.emailError
    var passwordError = registerVM.passwordError
    var phoneError = registerVM.phoneError
    var addressError = registerVM.addressError
    var passwordConfirmError = registerVM.passwordConfirmError

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
        LazyColumn (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item{
                Image(
                    painter = painterResource(R.drawable.ic_splash_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                )

                Spacer(modifier = Modifier.size(10.dp))

                when (registerVM.registerState) {
                    is RegisterState.Idle -> {
                    }
                    is RegisterState.Success -> {
                        Text("Register Successful!", color = Color.Green)
                    }
                    is RegisterState.Error -> {
                        Text((registerVM.registerState as RegisterState.Error).message, color = Color.Red)
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                TextFieldUI(
                    value = registerVM.name,
                    onValueChange = {
                        registerVM.onNameChange(it)
                        registerVM.validateName()
                    },
                    label = "Enter Your Name",
                    leadingIconId = R.drawable.ic_person,
                    keyboardType = KeyboardType.Text,
                    errorMessage = nameError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = registerVM.email,
                    onValueChange = {
                        registerVM.onEmailChange(it)
                        registerVM.validateEmail()
                    },
                    label = "Enter Your Email",
                    leadingIconId = R.drawable.ic_email,
                    keyboardType = KeyboardType.Email,
                    errorMessage = emailError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = registerVM.phone,
                    onValueChange = {
                        registerVM.onPhoneChange(it)
                        registerVM.validatePhone()
                    },
                    label = "Enter Your Phone Number",
                    leadingIconId = R.drawable.ic_phone,
                    keyboardType = KeyboardType.Phone,
                    errorMessage = phoneError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = registerVM.address,
                    onValueChange = {
                        registerVM.onAddressChange(it)
                        registerVM.validateAddress()
                    },
                    label = "Enter Your Address",
                    leadingIconId = R.drawable.ic_location,
                    keyboardType = KeyboardType.Text,
                    errorMessage = addressError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = registerVM.password,
                    onValueChange = {
                        registerVM.onPasswordChange(it)
                        registerVM.validatePassword()
                    },
                    label = "Enter Your Password",
                    leadingIconId = R.drawable.ic_lock,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    isPasswordVisible = registerVM.isPasswordVisible,
                    onPasswordVisibilityToggle = registerVM::togglePasswordVisibility,
                    errorMessage = passwordError
                )

                Spacer(modifier = Modifier.size(20.dp))

                TextFieldUI(
                    value = registerVM.confirm_password,
                    onValueChange = {
                        registerVM.onPasswordConfirmChange(it)
                        registerVM.validatePasswordConfirmation()
                    },
                    label = "Enter Confirmation Password",
                    leadingIconId = R.drawable.ic_lock,
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    isPasswordVisible = registerVM.isPasswordConfirmationVisible,
                    onPasswordVisibilityToggle = registerVM::togglePasswordConfirmationVisibility,
                    errorMessage = passwordConfirmError
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

                if (registerVM.isLoading) {
                    ButtonUI(
                        text = "Loading...",
                    ) {}
                } else {
                    ButtonUI(
                        text = "Register",
                        onClick ={
                            if (registerVM.validateFields()){
                                registerVM.register(navController = navController)
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
                        text = "Alreadt have account? ",
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

@Preview(showBackground = true)
@Composable
private fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    val customerRepo = CustomerRepository()
    val registerVM = RegisterVM(customerRepo)
    RegisterScreen(navController = navController, registerVM = registerVM)
}