package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.data.dto.recommendation.RecommendationRequest
import com.example.agrifuture.presentation.data.dto.recommendation.RecommendationResponse
import com.example.agrifuture.presentation.service.RecommendationService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecommendationRepository {
    private val recommendationService = ApiClient.recomendationService
    private val gson = Gson()

    suspend fun recommended(jenis_tanah: String, jenis_tanaman: String) : RecommendationResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = recommendationService.recommendation(
                    RecommendationRequest(jenis_tanah, jenis_tanaman)
                )
                response
            } catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.toString()
                val errResp = gson.fromJson(errorBody, RecommendationResponse::class.java)
                throw Exception(errResp.recommended_pupuk)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}