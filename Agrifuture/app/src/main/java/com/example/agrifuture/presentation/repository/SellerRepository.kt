package com.example.agrifuture.presentation.repository

import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Seller

class SellerRepository {
    private val sellers = listOf(
        Seller(1, "Daniel", "daniel@gmail.com","daniel123", "Riau", "081260799731"),
        Seller(2, "Bless", "bless@gmail.com","bless123", "Tarutung", "081260799732"),
        Seller(3, "Oped", "oped@gmail.com","oped123", "Tarutung-Riau", "081260799733"),
        Seller(4, "Pedcuu", "Pedcuu@gmail.com","pedcuul123", "Riau-Tarutung", "081260799734"),
    )

    fun getSellers(): List<Seller>{
        return sellers
    }
}