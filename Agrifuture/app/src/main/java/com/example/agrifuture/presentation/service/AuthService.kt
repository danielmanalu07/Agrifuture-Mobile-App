package com.example.agrifuture.presentation.service

import com.example.agrifuture.presentation.data.dto.login.LoginRequest
import com.example.agrifuture.presentation.data.dto.login.LoginResponse
import com.example.agrifuture.presentation.data.dto.logout.LogoutResponse
import com.example.agrifuture.presentation.data.dto.profile.ProfileResponse
import com.example.agrifuture.presentation.data.dto.register.RegisterRequest
import com.example.agrifuture.presentation.data.dto.register.RegisterResponse
import com.example.agrifuture.presentation.model.Customer
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @GET("profile")
    suspend fun profile(@Header ("Authorization") token: String): ProfileResponse

    @POST("logout")
    suspend fun logout(@Header ("Authorization") token: String) : LogoutResponse

}