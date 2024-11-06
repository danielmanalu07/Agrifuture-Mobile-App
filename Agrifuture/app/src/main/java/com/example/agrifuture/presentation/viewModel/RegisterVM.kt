package com.example.agrifuture.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.CustomerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterVM(private val customerRepository: CustomerRepository): ViewModel(){
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var confirm_password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isPasswordConfirmationVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var registerState by mutableStateOf<RegisterState>(RegisterState.Idle)

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
            email.equals(customerRepository.customers[0].email) -> "Email already exists"
            else -> null
        }
    }

    fun validatePhone(){
        phoneError = when {
            phone.isEmpty() -> "Phone is required"
            phone.equals(customerRepository.customers[0].phone) -> "Phone already exists"
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

    fun validateFields(): Boolean{
        validateName()
        validateEmail()
        validatePhone()
        validateAddress()
        validatePassword()
        validatePasswordConfirmation()

        return nameError == null && emailError == null && phoneError == null && addressError == null && passwordError == null && passwordConfirmError == null
    }


    fun register(navController: NavController){
        viewModelScope.launch {
            isLoading = true
            delay(1500)
            registerState = if (customerRepository.register(name, email, phone, address, password)){
                RegisterState.Success
            } else {
                RegisterState.Error("Invalid Registrasi")
            }
            isLoading = false

            delay(1000)
            if (registerState is RegisterState.Success){
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            }
        }
    }
}


sealed class RegisterState{
    object Idle : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}