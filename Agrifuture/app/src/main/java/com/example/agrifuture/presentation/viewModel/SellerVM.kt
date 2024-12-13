package com.example.agrifuture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrifuture.presentation.model.Seller
import com.example.agrifuture.presentation.repository.SellerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SellerVM: ViewModel() {
    private val sellerRepository = SellerRepository()

    fun getSellers() : List<Seller> {
        return sellerRepository.getSellers()
    }
}