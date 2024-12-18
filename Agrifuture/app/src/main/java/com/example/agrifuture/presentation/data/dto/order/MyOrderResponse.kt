package com.example.agrifuture.presentation.data.dto.order

import com.example.agrifuture.presentation.model.Order_Items

data class MyOrderResponse(
    val message: String,
    val items: List<Order_Items>
)
