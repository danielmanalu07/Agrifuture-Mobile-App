package com.example.agrifuture.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrifuture.presentation.data.dto.category.CategoryResponse
import com.example.agrifuture.presentation.data.dto.login.LoginResponse
import com.example.agrifuture.presentation.model.Category
import com.example.agrifuture.presentation.repository.CategoryRepository
import com.example.agrifuture.presentation.state.CategoryState
import com.example.agrifuture.presentation.state.LoginStates
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryVM: ViewModel(){
    private val categoryRepository = CategoryRepository()

    fun getCategories() : List<Category>{
        return categoryRepository.getCategory()
    }

    private var _categoryState = MutableStateFlow<CategoryState>(CategoryState.Idle)
    val category: StateFlow<CategoryState> get() = _categoryState
    private val gson = Gson()

    init {
        getCategory()
    }


    fun getCategory() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                val response = categoryRepository.getCategories()
                _categoryState.value = CategoryState.Success(
                    message = "true",
                    kategori = response.kategori
                )
                Log.d("catRes", response.kategori.toString())
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.toString()
                val errorMessage = gson.fromJson(errorBody, CategoryResponse::class.java)
                _categoryState.value = CategoryState.Error(errorMessage.message)
            } catch (e: Exception) {
                _categoryState.value = CategoryState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun getById(id: Int) {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                val res = categoryRepository.getById(id = id)
                _categoryState.value = CategoryState.Success(
                    message = "true",
                    kategori = res.kategori
                )
            } catch (e: retrofit2.HttpException) {
                val errorBody = e.response()?.errorBody()?.toString()
                val errorMsg = gson.fromJson(errorBody, CategoryResponse::class.java)
                _categoryState.value = CategoryState.Error(errorMsg.message)
            } catch (e: Exception) {
                _categoryState.value = CategoryState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}