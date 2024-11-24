package com.example.agrifuture.presentation.model


data class Notification(
    val id: Int,
    val title: String,
    val text: String,
    val readAt: Boolean,
    val type: String,
    val createdAt: String
)