package com.example.agrifuture.presentation.model


data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
    val photo: String?,
    val remember_token: String?,
)
