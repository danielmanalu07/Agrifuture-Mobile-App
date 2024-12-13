package com.example.agrifuture.presentation.model

data class Pupuk(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val category_id: Int,
    val seller_id: Int,
    val image_path: String,
    val stock: Int,
    val created_at: String,
    val updated_at: String,
    val sellers: Sellers?
)
