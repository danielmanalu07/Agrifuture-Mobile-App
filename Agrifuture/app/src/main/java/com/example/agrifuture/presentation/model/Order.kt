package com.example.agrifuture.presentation.model

data class Order(
    val id : Int,
    val customer: Customer,
    val total_price : Double,
)