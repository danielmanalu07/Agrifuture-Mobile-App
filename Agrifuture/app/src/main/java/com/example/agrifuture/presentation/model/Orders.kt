package com.example.agrifuture.presentation.model

data class Orders(
    val id: Int,
    val total_price: Float,
    val status: Status,
    val customer: Customer?
)

enum class Status {
    pending,
    cancled,
    completed
}

data class Order_Items(
    val id: Int,
    val order_id: Int,
    val fertilizer_id: Int,
    var quantity: Int,
    val price: Float,
    var Status: Boolean,
    val fertilizer: Pupuk?
)
