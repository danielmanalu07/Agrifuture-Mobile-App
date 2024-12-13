package com.example.agrifuture.presentation.data.dto.category

import com.example.agrifuture.presentation.model.Category

data class CategoryResponse(
    val message: String,
    val kategori: List<Category>
)