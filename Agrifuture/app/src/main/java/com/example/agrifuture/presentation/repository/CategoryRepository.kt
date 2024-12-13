package com.example.agrifuture.presentation.repository

import android.content.Context
import android.util.Log
import com.example.agrifuture.R
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.data.dto.category.CategoryResponse
import com.example.agrifuture.presentation.data.dto.profile.ProfileResponse
import com.example.agrifuture.presentation.model.Category
import com.example.agrifuture.presentation.service.CategoryService
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CategoryRepository {
    val categories = listOf(
        Category(1, "organik", "Pupuk", "https://storage.googleapis.com/pkg-portal-bucket/images/news/2019-03/pupuk-organik-demi-keberlanjutan-pertanian/pg_pupuk-petroganik.jpg"),
        Category(2, "anorganik", "Pupuk", "https://storage.googleapis.com/pkg-portal-bucket/images/news/2019-03/pupuk-organik-demi-keberlanjutan-pertanian/pg_pupuk-petroganik.jpg"),
        Category(3, "spesial", "Pupuk", "https://storage.googleapis.com/pkg-portal-bucket/images/news/2019-03/pupuk-organik-demi-keberlanjutan-pertanian/pg_pupuk-petroganik.jpg"),
        Category(4, "cair", "Pupuk", "https://storage.googleapis.com/pkg-portal-bucket/images/news/2019-03/pupuk-organik-demi-keberlanjutan-pertanian/pg_pupuk-petroganik.jpg"),
        Category(5, "cair", "Pupuk", "https://storage.googleapis.com/pkg-portal-bucket/images/news/2019-03/pupuk-organik-demi-keberlanjutan-pertanian/pg_pupuk-petroganik.jpg"),
    )

    fun getCategory(): List<Category>{
        return categories
    }

    private val categoryService = ApiClient.categoryService
    private val gson = Gson()

    suspend fun getCategories(): CategoryResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = categoryService.categories()
                Log.d("CategoryRepository", "Response: ${response}")
                response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.toString()
                val errorResponse = gson.fromJson(errorBody, CategoryResponse::class.java)
                throw Exception(errorResponse.message)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getById(id: Int) : CategoryResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = categoryService.getById(id)
                response
            } catch (e: HttpException) {
                val error = e.response()?.errorBody()?.toString()
                val errResponse = gson.fromJson(error, CategoryResponse::class.java)
                throw Exception(errResponse.message)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}