package com.example.agrifuture.presentation.model

data class Sellers(
    val id: Int,
    val name: String,
    val store_name: String,
    val phone: String,
    val address: String,
    val birth_date: String,
    val email: String,
    val gender: String,
    val profile_pic: String?,
    val username: String,
    val password: String,
    val role: String,
    val approved: Int,
    val created_at: String
)
