package com.example.agrifuture.presentation.repository

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.data.dto.login.LoginRequest
import com.example.agrifuture.presentation.data.dto.login.LoginResponse
import com.example.agrifuture.presentation.data.dto.profile.ProfileResponse
import com.example.agrifuture.presentation.data.dto.register.RegisterRequest
import com.example.agrifuture.presentation.data.dto.register.RegisterResponse
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.service.AuthService
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

class CustomerRepository {
    val customers = mutableListOf(
        Customer(1, "Daniel", "daniel@gmail.com", "085762649422", "Tarutung", "daman12345", null, null)
    )

    var isLoggedIn = false

    fun login(email: String, password: String): Boolean {
        isLoggedIn = customers.any{ it.email == email && it.password == password }
        return isLoggedIn
    }

    fun register(name: String, email: String, phone: String, address: String, password: String): Boolean{
        if (customers.any{it.email == email && it.phone == phone}){
            return false
        }
        customers.add(Customer(2, name, email, phone, address, password, null, null))
        return true
    }
}

class AuthRepository {
    private val authService = ApiClient.authService
    private val gson = Gson()
    suspend fun register(name: String, email: String, phone: String, address: String, password: String): RegisterResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = authService.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        phone = phone,
                        address = address,
                        password = password
                    )
                )
                response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = gson.fromJson(errorBody, RegisterResponse::class.java)
                throw Exception(errorResponse.message)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun login(email: String, password: String): LoginResponse{
        return withContext(Dispatchers.IO){
            authService.login(LoginRequest(email, password))
        }
    }

    suspend fun profile(context: Context): ProfileResponse {
        val authenticationUtils = AuthenticationUtils(context)
        return withContext(Dispatchers.IO) {
            try {
                val token = "Bearer " + authenticationUtils.getToken().toString()
                authService.profile(token)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = gson.fromJson(errorBody, ProfileResponse::class.java)
                throw Exception(errorResponse.message)
            } catch (e: Exception) {
                throw e
            }
        }
    }
}