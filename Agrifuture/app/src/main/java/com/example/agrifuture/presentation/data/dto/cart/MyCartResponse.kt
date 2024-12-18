package com.example.agrifuture.presentation.data.dto.cart

import com.example.agrifuture.presentation.model.Cart_Items
import com.example.agrifuture.presentation.model.Carts

data class MyCartResponse(
    val message: String,
    val items: List<Cart_Items>
)
