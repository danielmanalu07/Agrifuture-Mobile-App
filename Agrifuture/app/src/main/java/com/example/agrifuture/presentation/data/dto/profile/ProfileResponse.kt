package com.example.agrifuture.presentation.data.dto.profile

import com.example.agrifuture.presentation.model.Customer

data class ProfileResponse(
    val message: String,
    val data: Customer?,
    val success: Boolean = false
)