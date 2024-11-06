package com.example.agrifuture.presentation.model

import org.jetbrains.annotations.Nullable

data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val password: String,
    val photo: Nullable?,
    val remember_token: Nullable?
)
