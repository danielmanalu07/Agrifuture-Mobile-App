package com.example.agrifuture.presentation.repository

import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.viewModel.CategoryVM
import com.example.agrifuture.presentation.viewModel.ShopVM

class ProductRepository {
    val shopVM = ShopVM()
    val categoryVM = CategoryVM()
    val shop = shopVM.getShops()
    val category = categoryVM.getCategories()

    private var products = listOf(
        Product(1, shop[0], category[0], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen), P (Fosfat), dan K (Kalium) yang sangat berguna untuk tanaman. Bahan baku utama yang digunakan adalah urea produksi Pusri, Rock Phosphate, dan KCl. Keunggulan teknologi Pabrik NPK Fusion adalah fleksibilitas untuk dapat memproduksi berbagai macam formula dengan biaya investasi yang rendah.", 25000.00, 40, R.drawable.ic_product_1, 4),
        Product(2, shop[1], category[1], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 20, R.drawable.ic_product_2, 5),
        Product(3, shop[2], category[2], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 30, R.drawable.ic_product_3, 3),
        Product(4, shop[3], category[3], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 10, R.drawable.ic_product_4, 3),
        Product(5, shop[1], category[0], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 60, R.drawable.ic_product_1, 4),
        Product(6, shop[1], category[0], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 60, R.drawable.ic_product_1, 4),
        Product(7, shop[1], category[0], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 60, R.drawable.ic_product_1, 5),
        Product(8, shop[1], category[0], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 60, R.drawable.ic_product_1, 3),
        Product(9, shop[2], category[1], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 5, R.drawable.ic_product_2, 2),
        Product(10, shop[2], category[1], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 5, R.drawable.ic_product_2, 1),
        Product(11, shop[2], category[1], "Pupuk UREA 46%", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 5, R.drawable.ic_product_2, 5),
        Product(12, shop[2], category[1], "NPK Mutiara", "Pupuk NPK adalah pupuk majemuk yang mengandung unsur hara N (Nitrogen)", 25000.00, 5, R.drawable.ic_product_2, 4),
    )

    fun getProducts() : List<Product> {
        return products
    }

}