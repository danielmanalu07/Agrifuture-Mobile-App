package com.example.agrifuture.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrifuture.presentation.repository.RecommendationRepository
import com.example.agrifuture.presentation.state.RecommendationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class RecommendationVM(private val recommendationRepository: RecommendationRepository): ViewModel() {
    var jenis_tanah by mutableStateOf("")
    var jenis_tanahError by mutableStateOf<String?>(null)
    var jenis_tanaman by mutableStateOf("")
    var jenis_tanamanError by mutableStateOf<String?>(null)

    fun onJenisTanahChange(j_tanah: String) {
        jenis_tanah = j_tanah
    }

    fun onJenisTanamanChange(j_tanaman: String){
        jenis_tanaman = j_tanaman
    }

    fun validateJenisTanah(){
        jenis_tanahError = when {
            jenis_tanah.isEmpty() -> "Jenis Tanah Harus Diisi"
            else -> null
        }
    }

    fun validateJenisTanaman() {
        jenis_tanamanError = when {
            jenis_tanaman.isEmpty() -> "Jenis Tanaman Harus Diisi"
            else -> null
        }
    }

    fun validateField(): Boolean {
        validateJenisTanah()
        validateJenisTanaman()

        return listOf(
            jenis_tanahError,
            jenis_tanamanError
        ).all { it == null }
    }

    private val _recommendedState = MutableStateFlow<RecommendationState>(RecommendationState.Idle)
    val recommendedState: StateFlow<RecommendationState> get() = _recommendedState

    fun recommendation() {
        if (!validateField()){
            _recommendedState.value = RecommendationState.Error("Semua inputan Harus diisi")
            return
        }

        _recommendedState.value = RecommendationState.Loading
        viewModelScope.launch {
            try {
                val response = recommendationRepository.recommended(jenis_tanah, jenis_tanaman)
                if (response.recommended_pupuk.isNotEmpty()) {
                    _recommendedState.value = RecommendationState.Success(response.recommended_pupuk)
                } else {
                    _recommendedState.value = RecommendationState.Error("Respons kosong")
                }
            } catch (e: SocketTimeoutException) {
                _recommendedState.value = RecommendationState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _recommendedState.value = RecommendationState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
