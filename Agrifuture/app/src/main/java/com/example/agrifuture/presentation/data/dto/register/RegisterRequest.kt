package com.example.agrifuture.presentation.data.dto.register

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
)
