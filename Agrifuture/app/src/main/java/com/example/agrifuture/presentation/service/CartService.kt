package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.data.dto.cart.AddToCartRequest
import com.example.agrifuture.presentation.data.dto.cart.AddToCartResponse
import com.example.agrifuture.presentation.data.dto.cart.MyCartResponse
import com.example.agrifuture.presentation.data.dto.cart.UpdateQuantityRequest
import com.example.agrifuture.presentation.data.dto.cart.UpdateQuantityResponse
import com.example.agrifuture.presentation.data.dto.cart.UpdateStatusRequest
import com.example.agrifuture.presentation.data.dto.cart.UpdateStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartService {
    @POST("cart/addToCart")
    suspend fun addToCart(@Body requestBody: AddToCartRequest, @Header ("Authorization") token: String) : AddToCartResponse

    @GET("cart/cart-items/myCart")
    suspend fun myCart(@Header ("Authorization") token: String) : MyCartResponse

    @PATCH("cart/cart-items/{cartItemId}/quantity")
    suspend fun updateQuantity(@Path("cartItemId") cartItemId: Int, @Body requestBody: UpdateQuantityRequest, @Header ("Authorization") token: String) : UpdateQuantityResponse

    @PATCH("cart/cart-items/{cartItemId}/status")
    suspend fun updateStatus(@Path("cartItemId") cartItemId: Int, @Body requestBody: UpdateStatusRequest, @Header ("Authorization") token: String) : UpdateStatusResponse
}