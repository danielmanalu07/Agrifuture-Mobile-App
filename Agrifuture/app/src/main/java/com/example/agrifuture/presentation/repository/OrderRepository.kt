package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.model.Order
import com.example.agrifuture.presentation.viewModel.CustomerVM

class OrderRepository {
    val customerVM = CustomerVM()
    val customer = customerVM.getCustomers()
    private val orders = listOf(
        Order(1, customer[0], 800000.00)
    )

    fun getOrders() : List<Order>{
        return orders
    }
}