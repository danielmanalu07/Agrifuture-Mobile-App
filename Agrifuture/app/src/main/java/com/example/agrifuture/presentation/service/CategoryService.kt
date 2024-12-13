package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.data.dto.category.CategoryResponse
import com.example.agrifuture.presentation.data.dto.profile.ProfileResponse
import com.example.agrifuture.presentation.model.Category
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryService {
    @GET("kategori")
    suspend fun categories(): CategoryResponse

    @GET("kategori/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ) : CategoryResponse
}