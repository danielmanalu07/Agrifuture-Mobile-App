package com.example.agrifuture.presentation.repository

import android.content.Context
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.data.dto.cart.AddToCartRequest
import com.example.agrifuture.presentation.data.dto.cart.AddToCartResponse
import com.example.agrifuture.presentation.data.dto.cart.MyCartResponse
import com.example.agrifuture.presentation.data.dto.cart.UpdateQuantityRequest
import com.example.agrifuture.presentation.data.dto.cart.UpdateQuantityResponse
import com.example.agrifuture.presentation.data.dto.cart.UpdateStatusRequest
import com.example.agrifuture.presentation.data.dto.cart.UpdateStatusResponse
import com.example.agrifuture.presentation.data.dto.recommendation.RecommendationResponse
import com.example.agrifuture.presentation.model.Cart
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.example.agrifuture.presentation.viewModel.CustomerVM
import com.example.agrifuture.presentation.viewModel.ProductVM
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CartRepository {
    val productVM = ProductVM()
    val product = productVM.getProducts()

    val customerVM = CustomerVM()
    val customer = customerVM.getCustomers()

    private val carts = listOf(
        Cart(1, product[0], customer[0], 3, false),
        Cart(2, product[1], customer[0], 2, false),
        Cart(3, product[2], customer[0], 1, false),
    )

    fun getCarts() : List<Cart>{
        return carts
    }

    private val cartService = ApiClient.cartService
    private val gson = Gson()

    suspend fun addToCart(fertilizerId: Int, context: Context): AddToCartResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO) {
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                cartService.addToCart(
                    AddToCartRequest(fertilizerId),
                    "Bearer $token"
                )
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, AddToCartResponse::class.java)
                throw Exception(errResp?.message ?: "HTTP error")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun myCart(context: Context) : MyCartResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO) {
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                val response = cartService.myCart("Bearer $token")
                response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, MyCartResponse::class.java)
                throw Exception(errResp?.message ?: "HTTP error")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun updateQuantity(cartItemId:Int, quantity:Int, context: Context): UpdateQuantityResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO){
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                cartService.updateQuantity(
                    cartItemId,
                    UpdateQuantityRequest(quantity),
                    "Bearer $token"
                )
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, UpdateQuantityResponse::class.java)
                throw Exception(errResp?.message ?: "HTTP error")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun updateStatus(cartItemId:Int, status:Boolean, context: Context): UpdateStatusResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO){
            try {
                val token = authenticationUtils.getToken() ?: throw Exception("Token not found")
                cartService.updateStatus(
                    cartItemId,
                    UpdateStatusRequest(status),
                    "Bearer $token"
                )
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errResp = gson.fromJson(errorBody, UpdateStatusResponse::class.java)
                throw Exception(errResp?.message ?: "HTTP error")
            } catch (e: Exception) {
                throw e
            }
        }
    }
}