package com.example.agrifuture.presentation.repository

import com.example.agrifuture.R
import com.example.agrifuture.presentation.model.Category

class CategoryRepository {
    val categories = listOf(
        Category(1, "organik", "Pupuk", R.drawable.ic_category1),
        Category(2, "anorganik", "Pupuk", R.drawable.ic_category1),
        Category(3, "spesial", "Pupuk", R.drawable.ic_category1),
        Category(4, "cair", "Pupuk", R.drawable.ic_category1),
        Category(5, "cair", "Pupuk", R.drawable.ic_category1),
    )

    fun getCategory(): List<Category>{
        return categories
    }
}