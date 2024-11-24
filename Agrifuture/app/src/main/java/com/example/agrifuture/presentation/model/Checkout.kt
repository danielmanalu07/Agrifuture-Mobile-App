package com.example.agrifuture.presentation.model

data class Checkout(
    val id : Int,
    val cart: Cart,
    val ongkir: Double,
    val status: Boolean,
)