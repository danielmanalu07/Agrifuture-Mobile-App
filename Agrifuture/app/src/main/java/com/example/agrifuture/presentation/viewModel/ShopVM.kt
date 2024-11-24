package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Shop
import com.example.agrifuture.presentation.repository.ShopRepository

class ShopVM: ViewModel() {
    private val shopRepository = ShopRepository()

    fun getShops(): List<Shop>{
        return shopRepository.getShops()
    }
}