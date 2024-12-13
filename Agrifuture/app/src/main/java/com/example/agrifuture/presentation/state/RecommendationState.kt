package com.example.agrifuture.presentation.state

import com.example.agrifuture.presentation.model.Customer

sealed class RecommendationState {
    object Idle : RecommendationState()
    object Loading : RecommendationState()
    data class Success(
        val recommended_pupuk: String,
    ) : RecommendationState()
    data class Error(val message: String) : RecommendationState()
}