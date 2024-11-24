package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.model.Cart
import com.example.agrifuture.presentation.viewModel.CustomerVM
import com.example.agrifuture.presentation.viewModel.ProductVM

class CartRepository {
    val productVM = ProductVM()
    val product = productVM.getProducts()

    val customerVM = CustomerVM()
    val customer = customerVM.getCustomers()

    private val carts = listOf(
        Cart(1, product[0], customer[0], 3, false),
        Cart(2, product[1], customer[0], 2, false),
        Cart(3, product[2], customer[0], 1, false),
    )

    fun getCarts() : List<Cart>{
        return carts
    }
}