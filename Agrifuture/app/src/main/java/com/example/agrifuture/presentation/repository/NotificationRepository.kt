package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.model.Notification

class NotificationRepository {
    private var notifications = listOf(
        Notification(1,"Payment Successful", "Success! Your payment has been processed.", false, "payment", "06/11/2024 14:30:00"),
        Notification(2,"Order confirmed", "Thank you! Your order was successfully created.", false, "order" ,"07/11/2024 14:30:00"),
        Notification(3,"Payment Successful", "Success! Your payment has been processed.", false, "payment", "06/11/2024 14:30:00"),
        Notification(4,"Order confirmed", "Thank you! Your order was successfully created.", false, "order" ,"07/11/2024 14:30:00")
    )

    fun getNotifications(): List<Notification>{
        return notifications
    }
}