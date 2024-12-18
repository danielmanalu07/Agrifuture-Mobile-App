package com.example.agrifuture.presentation.state

import com.example.agrifuture.presentation.model.Order_Items

sealed class OrderState {
    object Idle: OrderState()
    object Loading: OrderState()
    data class Success(
        val messsage: String,
    ) : OrderState()
    data class Error(
        val messsage: String
    ) : OrderState()
}

sealed class MyOrderState{
    object Idle: MyOrderState()
    object Loading: MyOrderState()
    data class Success(
        val messsage: String,
        val items: List<Order_Items>
    ) : MyOrderState()
    data class Error(
        val messsage: String
    ) : MyOrderState()
}