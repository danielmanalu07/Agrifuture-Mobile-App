package com.example.agrifuture.presentation.state

import com.example.agrifuture.presentation.model.Category
import kotlinx.coroutines.flow.MutableStateFlow

sealed class CategoryState {
    object Idle : CategoryState()
    object Loading : CategoryState()
    data class Success(
        val message: String,
        val kategori: List<Category>
    ) : CategoryState()
    data class Error(val message: String) : CategoryState()
}