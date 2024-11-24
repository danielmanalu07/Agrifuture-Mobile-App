package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Cart
import com.example.agrifuture.presentation.repository.CartRepository
import com.example.agrifuture.presentation.repository.CategoryRepository

class CartVM: ViewModel() {
    val cartRepository = CartRepository()

    fun getCarts() : List<Cart>{
        return cartRepository.getCarts()
    }
}