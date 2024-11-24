package com.example.agrifuture.presentation.model

data class Shop(
    val id: Int,
    val seller: Seller,
    val name: String,
    val description: String,
    val address: String,
)