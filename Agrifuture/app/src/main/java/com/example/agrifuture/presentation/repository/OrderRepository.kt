package com.example.agrifuture.presentation.repository

import android.content.Context
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.data.dto.cart.AddToCartResponse
import com.example.agrifuture.presentation.data.dto.order.MyOrderResponse
import com.example.agrifuture.presentation.data.dto.order.OrderResponse
import com.example.agrifuture.presentation.model.Order
import com.example.agrifuture.presentation.model.Orders
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.example.agrifuture.presentation.viewModel.CustomerVM
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class OrderRepository {
    val customerVM = CustomerVM()
    val customer = customerVM.getCustomers()
    private val orders = listOf(
        Order(1, customer[0], 800000.00)
    )

    fun getOrders() : List<Order>{
        return orders
    }

    private val orderService = ApiClient.orderService
    private val gson = Gson()

    suspend fun addToOrder(context: Context): OrderResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO){
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                val response = orderService.addToOrder("Bearer $token")
                response
            }catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, OrderResponse::class.java)
                throw Exception(errResp?.message ?: "HTTP error")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun myOrder(context: Context) : MyOrderResponse{
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO){
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                val response = orderService.myOrder("Bearer $token")
                response
            }catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, MyOrderResponse::class.java)
                throw Exception(errResp?.message ?: "Error: ${e.code()}")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun myOrderPending(context: Context) : Orders {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO){
            val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
            try {
                val response = orderService.myOrderPending(
                    "Bearer $token"
                )
                response
            }catch (e: Exception) {
                throw Exception("Failed to fetch order: ${e.message}", e)
            }
        }
    }

}