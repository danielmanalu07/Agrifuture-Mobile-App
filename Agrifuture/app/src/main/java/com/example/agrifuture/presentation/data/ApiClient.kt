package com.example.agrifuture.presentation.data

import com.example.agrifuture.presentation.service.AuthService
import com.example.agrifuture.presentation.service.CategoryService
import com.example.agrifuture.presentation.service.PupukService
import com.example.agrifuture.presentation.service.RecommendationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val BASE_URL_1 = "http://10.0.2.2:3000/api/"
    const val BASE_URL_2 = "http://10.0.2.2:3001/api/"
    const val URL_RECOMMENDED = "https://prediksi-pupuk.1p3jnco58a14.us-south.codeengine.appdomain.cloud/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        createRetrofit(BASE_URL_1).create(AuthService::class.java)
    }

    val categoryService: CategoryService by lazy {
        createRetrofit(BASE_URL_2).create(CategoryService::class.java)
    }

    val recomendationService: RecommendationService by lazy {
        createRetrofit(URL_RECOMMENDED).create(RecommendationService::class.java)
    }

    val pupukService: PupukService by lazy {
        createRetrofit(BASE_URL_2).create(PupukService::class.java)
    }
}