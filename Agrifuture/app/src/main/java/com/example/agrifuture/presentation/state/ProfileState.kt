package com.example.agrifuture.presentation.state

import com.example.agrifuture.presentation.model.Customer

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(
        val message: String,
        val data: Customer
    ) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
