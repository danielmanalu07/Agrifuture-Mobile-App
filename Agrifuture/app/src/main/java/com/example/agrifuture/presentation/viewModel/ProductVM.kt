package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Product
import com.example.agrifuture.presentation.repository.ProductRepository

class ProductVM: ViewModel() {
    val productRepository = ProductRepository()

    fun getProducts(): List<Product>{
        return productRepository.getProducts()
    }

    fun getProductsForCategory(categoryId: Int): List<Product> {
        return productRepository.getProducts().filter { it.category.id == categoryId }
    }
}