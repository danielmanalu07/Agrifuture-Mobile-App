package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.model.Checkout
import com.example.agrifuture.presentation.viewModel.CartVM

class CheckoutRepository {
    val cartVM = CartVM()
    val cart = cartVM.getCarts()

    private val checkouts = listOf(
        Checkout(1, cart[0], 10000.00, false),
        Checkout(2, cart[1], 10000.00, false)
    )

    fun getCheckouts() : List<Checkout>{
        return checkouts
    }
}