package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Checkout
import com.example.agrifuture.presentation.repository.CheckoutRepository

class CheckoutVM: ViewModel() {
    val checkoutRepository = CheckoutRepository()

    fun getCheckouts() : List<Checkout> {
        return checkoutRepository.getCheckouts()
    }
}