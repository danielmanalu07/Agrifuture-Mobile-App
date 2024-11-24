package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Seller
import com.example.agrifuture.presentation.repository.SellerRepository

class SellerVM: ViewModel() {
    private val sellerRepository = SellerRepository()

    fun getSellers() : List<Seller> {
        return sellerRepository.getSellers()
    }
}