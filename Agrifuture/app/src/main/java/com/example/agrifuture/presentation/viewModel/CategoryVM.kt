package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Category
import com.example.agrifuture.presentation.repository.CategoryRepository

class CategoryVM: ViewModel(){
    private val categoryRepository = CategoryRepository()

    fun getCategories() : List<Category>{
        return categoryRepository.getCategory()
    }
}