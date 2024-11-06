package com.example.agrifuture.presentation.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginVM(private val customerRepository: CustomerRepository,  private val context: Context) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var loginState by mutableStateOf<LoginState>(LoginState.Idle)

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)




    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun onEmailChange(input_email: String){
        email = input_email
    }

    fun onPasswordChange(input_password: String){
        password = input_password
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateEmail(){
        emailError = when{
            email.isEmpty() -> "Email is Required"
            !isValidEmail(email) -> "Invlaid email Format"
            else -> null
        }
    }

    fun validatePassword(){
        passwordError = when{
            password.isEmpty() -> "Password is Required"
            else -> null
        }
    }

    fun validateFields(): Boolean{
        validateEmail()
        validatePassword()

        return emailError == null && passwordError == null
    }

    fun login(navController: NavController){
        viewModelScope.launch {
            isLoading = true
            delay(1500)
            loginState = if (customerRepository.login(email, password)) {
                LoginState.Success
            } else {
                LoginState.Error("Invalid Credentials")
            }
            isLoading = false

            delay(1000)
            if (loginState is LoginState.Success){
                val authUtils = AuthenticationUtils(context)
                authUtils.setLoginComplete()
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
    }
}

sealed class LoginState{
    object Idle : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}