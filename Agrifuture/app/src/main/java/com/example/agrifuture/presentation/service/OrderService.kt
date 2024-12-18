package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.data.dto.order.MyOrderResponse
import com.example.agrifuture.presentation.data.dto.order.OrderResponse
import com.example.agrifuture.presentation.model.Orders
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderService {
    @POST("order/adddToOrder")
    suspend fun addToOrder(@Header("Authorization") token: String) : OrderResponse

    @GET("order/myOrder")
    suspend fun myOrder(@Header("Authorization") token: String) : MyOrderResponse

    @GET("order/myOrderPending")
    suspend fun myOrderPending(@Header("Authorization") token: String) : Orders
}