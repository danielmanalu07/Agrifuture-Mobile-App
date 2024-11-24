package com.example.agrifuture.presentation.model

data class Cart(
    val id: Int,
    val product: Product,
    val customer: Customer,
    var quantity: Int,
    var status: Boolean,
)