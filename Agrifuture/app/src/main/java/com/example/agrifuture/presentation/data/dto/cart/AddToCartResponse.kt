package com.example.agrifuture.presentation.data.dto.cart

import com.example.agrifuture.presentation.model.Carts

data class AddToCartResponse(
    val message: String,
    val item: Carts
)
