package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.data.dto.recommendation.RecommendationRequest
import com.example.agrifuture.presentation.data.dto.recommendation.RecommendationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationService {
    @POST("predict")
    suspend fun recommendation(@Body request: RecommendationRequest): RecommendationResponse
}