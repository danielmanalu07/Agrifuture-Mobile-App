package com.example.agrifuture.presentation.model

data class Product(
    val id: Int,
    val shop: Shop,
    val category: Category,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val image: Int,
    val rating: Int
)