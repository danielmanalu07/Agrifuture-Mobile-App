package com.example.agrifuture.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrifuture.presentation.data.dto.login.LoginResponse
import com.example.agrifuture.presentation.data.dto.profile.ProfileResponse
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.service.AuthService
import com.example.agrifuture.presentation.state.AuthState
import com.example.agrifuture.presentation.state.LoginStates
import com.example.agrifuture.presentation.state.ProfileState
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthVM(private val repository: AuthRepository, var customer: List<Customer>, navController: NavController) : ViewModel() {
    private val _customer: MutableStateFlow<Customer?> = MutableStateFlow(null)
    val customers: StateFlow<Customer?> = _customer.asStateFlow()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var confirm_password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var authenticationState by mutableStateOf<AuthState>(AuthState.Idle)
    var isPasswordConfirmationVisible by mutableStateOf(false)

    var loginState by mutableStateOf<LoginStates>(LoginStates.Idle)
    var profileState by mutableStateOf<ProfileState>(ProfileState.Idle)
        private set
    private val gson = Gson()


    var nameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var phoneError by mutableStateOf<String?>(null)
    var addressError by mutableStateOf<String?>(null)
    var passwordConfirmError by mutableStateOf<String?>(null)

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun togglePasswordConfirmationVisibility() {
        isPasswordConfirmationVisible = !isPasswordConfirmationVisible
    }

    fun onNameChange(input_name: String){
        name = input_name
    }

    fun onEmailChange(input_email: String){
        email = input_email
    }

    fun onPhoneChange(input_phone: String){
        phone = input_phone
    }

    fun onAddressChange(input_address: String){
        address = input_address
    }

    fun onPasswordChange(input_password: String){
        password = input_password
    }

    fun onPasswordConfirmChange(input_passwordConfirm: String){
        confirm_password = input_passwordConfirm
    }


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPhone(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }


    fun validateName(){
        nameError = when {
            name.isEmpty() -> "Name is required"
            else -> null
        }
    }

    fun validateEmail(){
        emailError = when{
            email.isEmpty() -> "Email is required"
            !isValidEmail(email) -> "Invalid email format"
            email.equals(customer.firstOrNull()?.email) -> "Email already exists"
            else -> null
        }
    }

    fun validateEmailLogin(){
        emailError = when{
            email.isEmpty() -> "Email is required"
            !isValidEmail(email) -> "Invalid email format"
            else -> null
        }
    }

    fun validatePhone(){
        phoneError = when {
            phone.isEmpty() -> "Phone is required"
            !isValidPhone(phone) -> "Invalid phone format"
            phone.equals(customer.firstOrNull()?.phone) -> "Phone already exists"
            else -> null
        }
    }

    fun validateAddress(){
        addressError = when {
            address.isEmpty() -> "Address is required"
            else -> null
        }
    }

    fun validatePassword(){
        passwordError = when {
            password.isEmpty() -> "Password is required"
            else -> null
        }
    }

    fun validatePasswordConfirmation(){
        passwordConfirmError = when {
            confirm_password.isEmpty() -> "Confirmation Password is required"
            !confirm_password.equals(password) -> "Confirmation Password doesn't match"
            else -> null
        }
    }

    fun validateFields(): Boolean {
        validateName()
        validateEmail()
        validatePhone()
        validateAddress()
        validatePassword()
        validatePasswordConfirmation()

        return listOf(
            nameError,
            emailError,
            phoneError,
            addressError,
            passwordError,
            passwordConfirmError
        ).all { it == null }
    }

    fun validateFieldsLogin(): Boolean {
        validateEmailLogin()
        validatePassword()

        return listOf(
            emailError,
            passwordError,
        ).all { it == null }
    }




    fun register(navController: NavController) {
        if (!validateFields()) {
            authenticationState = AuthState.Error("Please check all fields")
            return
        }
        authenticationState = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = repository.register(name.trim(), email.trim(), phone.trim(), address.trim(), password)
                if (response.message.contains("Registered Successfully")) {
                    authenticationState = AuthState.Success(response.message)
                    if (response.message == "Registered Successfully") {
                        delay(1000)
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                } else {
                    authenticationState = AuthState.Error(response.message)
                }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("Duplicate entry") == true -> {
                        if (e.message?.contains("email") == true) {
                            "Email already exists"
                        } else if (e.message?.contains("phone") == true) {
                            "Phone number already exists"
                        } else {
                            "User already exists"
                        }
                    }
                    e.message?.contains("Connection refused") == true -> "Server is unreachable"
                    else -> e.message ?: "Unknown error occurred"
                }
                authenticationState = AuthState.Error(errorMessage)
            }
        }
    }


    fun login(navController: NavController, context: Context){
        if (!validateFieldsLogin()) {
            loginState = LoginStates.Error("Please check all fields")
            return
        }
        loginState = LoginStates.Loading
        viewModelScope.launch {
            try {
                val response = repository.login(
                    email.trim(),
                    password.trim()
                )
                loginState = LoginStates.Success(
                    response.token,
                    response.message
                )
                delay(1500)
                val authUtils = AuthenticationUtils(context = context)
                authUtils.setToken(response.token)
                AuthenticationUtils(context).setToken(response.token)
                authUtils.setLoginComplete()
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route) {inclusive = true}
                }
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = gson.fromJson(errorBody, LoginResponse::class.java)
                loginState = LoginStates.Error(errorMessage.message)
            } catch (e: Exception) {
                loginState = LoginStates.Error(e.message ?: "Unknown error occurred")
            }
        }
    }



    fun profile(context: Context) = viewModelScope.launch {
        profileState = ProfileState.Loading
        try {
            val response = repository.profile(context)
            profileState = response.data?.let {
                ProfileState.Success(
                    message = response.message,
                    data = it
                )
            }!!
            Log.d("customer_data", response.data.name)
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = gson.fromJson(errorBody, ProfileResponse::class.java)
            profileState = ProfileState.Error(errorMessage.message)
        } catch (e: Exception) {
            profileState = ProfileState.Error(e.message ?: "Unknown error occurred")
        }
    }


    fun logout(context: Context) {
        val authUtils = AuthenticationUtils(context)
        authUtils.setLogout()
        _customer.value = null
    }

}