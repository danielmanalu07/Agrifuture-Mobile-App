package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Order
import com.example.agrifuture.presentation.repository.OrderRepository

class OrderVM: ViewModel() {
    val orderRepository = OrderRepository()

    fun getOrders() : List<Order>{
        return orderRepository.getOrders()
    }
}