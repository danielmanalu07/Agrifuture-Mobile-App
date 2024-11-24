package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Notification
import com.example.agrifuture.presentation.repository.NotificationRepository

class NotificationVM: ViewModel() {
    val notificationRepository = NotificationRepository()

    fun getNotifications(): List<Notification>{
        return notificationRepository.getNotifications()
    }
}