package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.model.Shop
import com.example.agrifuture.presentation.viewModel.SellerVM

class ShopRepository {
    val sellerVM = SellerVM()
    var seller = sellerVM.getSellers()

    private var shops = listOf(
        Shop(1, seller[0], "Jaman Tani", "Toko Pupuk Jaman Tani", "Porsea"),
        Shop(2, seller[1], "Subur Jaya", "Toko Pupuk Subur Jaya", "Balige"),
        Shop(3, seller[2], "Oped Jaya", "Toko Pupuk Oped Jaya", "Tarutung"),
        Shop(4, seller[3], "Pedcuu Jaya", "Toko Pupuk Pedcuu Jaya", "Riau")
    )

    fun getShops(): List<Shop> {
        return shops
    }
}