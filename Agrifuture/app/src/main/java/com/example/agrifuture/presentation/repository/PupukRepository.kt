package com.example.agrifuture.presentation.repository

import com.example.agrifuture.presentation.data.ApiClient
import com.example.agrifuture.presentation.model.Pupuk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PupukRepository {
    private val pupukService = ApiClient.pupukService

    suspend fun getPupuk(): List<Pupuk> {
        return withContext(Dispatchers.IO) {
            try {
                val response = pupukService.getPupuk()
                response
            } catch (e: Exception) {
                throw Exception("Failed to fetch pupuk: ${e.message}", e)
            }
        }
    }

    suspend fun getPupukById(id: Int): Pupuk {
        return withContext(Dispatchers.IO) {
            try {
                val response = pupukService.getPupukById(id)
                response
            } catch (e: Exception) {
                throw Exception("Failed to get pupuk: ${e.message}", e)
            }
        }
    }
}
