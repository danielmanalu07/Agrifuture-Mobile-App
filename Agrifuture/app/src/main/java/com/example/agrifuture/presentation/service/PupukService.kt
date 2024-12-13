package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.model.Pupuk
import retrofit2.http.GET
import retrofit2.http.Path

interface PupukService {
    @GET("pupuk")
    suspend fun getPupuk() : List<Pupuk>

    @GET("pupuk/{id}")
    suspend fun getPupukById(
        @Path("id") id: Int
    ) : Pupuk
}