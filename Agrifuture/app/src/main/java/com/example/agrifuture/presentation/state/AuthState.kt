package com.example.agrifuture.presentation.state


sealed class AuthState {
    object Idle: AuthState()
    object Loading: AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class LoginStates{
    object Idle : LoginStates()
    object Loading : LoginStates()
    data class Success(val token: String, val message: String) : LoginStates()
    data class Error(val message: String) : LoginStates()
}

