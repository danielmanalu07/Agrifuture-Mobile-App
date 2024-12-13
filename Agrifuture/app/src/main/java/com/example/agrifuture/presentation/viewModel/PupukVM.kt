package com.example.agrifuture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrifuture.presentation.model.Pupuk
import com.example.agrifuture.presentation.repository.PupukRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PupukVM(private val pupukRepository: PupukRepository) : ViewModel() {
    private val _pupukList = MutableStateFlow<List<Pupuk>>(emptyList())
    val pupukList: StateFlow<List<Pupuk>> = _pupukList.asStateFlow()
    private val gson = Gson()

    init {
        fetchPupuk()
    }

    fun fetchPupuk() {
        viewModelScope.launch {
            try {
                val pupukData = pupukRepository.getPupuk()
                _pupukList.value = pupukData
                Log.d("PupukVM", "Data fetched: ${gson.toJson(pupukData)}")
            } catch (e: Exception) {
                Log.e("PupukVM", "Error fetching pupuk: ${e.localizedMessage}", e)
            }
        }
    }

    fun getPupukById(id: Int) {
        viewModelScope.launch {
            try {
                val data = pupukRepository.getPupukById(id)
                _pupukList.value = listOf(data)
                Log.d("PupuById", gson.toJson(data))
            } catch (e: Exception){
                Log.e("PupukVM", "Error fetching pupuk: ${e.localizedMessage}", e)
            }
        }
    }

    private val _pupukByCategory = MutableStateFlow<List<Pupuk>>(emptyList())
    val pupukByCategory: StateFlow<List<Pupuk>> = _pupukByCategory.asStateFlow()

    fun getPupukByCategory(id: Int) {
        viewModelScope.launch {
            try {
                val allPupuk = pupukRepository.getPupuk()
                val filteredPupuk = allPupuk.filter { it.category_id == id }
                _pupukByCategory.emit(filteredPupuk)
                Log.d("PupukByCategory", "Filtered Pupuk: ${gson.toJson(filteredPupuk)}")
            } catch (e: Exception) {
                Log.e("PupukVM", "Error fetching pupuk by category: ${e.localizedMessage}", e)
            }
        }
    }



}
