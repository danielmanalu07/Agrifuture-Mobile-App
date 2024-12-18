package com.example.agrifuture.presentation.model

data class Carts(
    val id: Int,
    val user_id: Int,
)

data class Cart_Items(
    val id: Int,
    val cart_id: Int,
    val fertilizer_id: Int,
    var quantity: Int,
    val price: Float,
    var Status: Boolean,
    val fertilizer: Pupuk?
)