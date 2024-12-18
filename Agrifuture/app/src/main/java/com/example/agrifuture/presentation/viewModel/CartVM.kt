package com.example.agrifuture.presentation.viewModel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrifuture.presentation.model.Cart
import com.example.agrifuture.presentation.model.Carts
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.CartRepository
import com.example.agrifuture.presentation.repository.CategoryRepository
import com.example.agrifuture.presentation.state.CartState
import com.example.agrifuture.presentation.state.MyCartState
import com.example.agrifuture.presentation.state.RecommendationState
import com.example.agrifuture.presentation.state.UpdateQuantityState
import com.example.agrifuture.presentation.state.UpdateStatusState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.net.SocketTimeoutException

class CartVM: ViewModel() {
    val cartRepository = CartRepository()

    fun getCarts() : List<Cart>{
        return cartRepository.getCarts()
    }


    private val _addToCart = MutableStateFlow<CartState>(CartState.Idle)

    fun addToCart(fertilizerId: Int, context: Context, onNavigateToCart: () -> Unit) {
        _addToCart.value = CartState.Loading
        viewModelScope.launch {
            try {
                val response = cartRepository.addToCart(fertilizerId, context)
                _addToCart.value = CartState.Success(
                    message = response.message,
                    item = response.item
                )
                onNavigateToCart()
            } catch (e: SocketTimeoutException) {
                _addToCart.value = CartState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _addToCart.value = CartState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private val _myCartState = MutableStateFlow<MyCartState>(MyCartState.Idle)
    val myCartState: StateFlow<MyCartState> get() = _myCartState

    fun myCart(context: Context) {
        _myCartState.value = MyCartState.Loading
        viewModelScope.launch {
            try {
                val data = cartRepository.myCart(context)
                _myCartState.value = MyCartState.Success(
                    message = data.message,
                    items = data.items
                )
            }catch (e: SocketTimeoutException) {
                _myCartState.value = MyCartState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _myCartState.value = MyCartState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private val _updateQuantity = MutableStateFlow<UpdateQuantityState>(UpdateQuantityState.Idle)
    val updateQuantity: StateFlow<UpdateQuantityState> get() = _updateQuantity

    fun updateQuantity(cartItemId: Int, quantity: Int, context: Context) {
        _updateQuantity.value = UpdateQuantityState.Loading
        viewModelScope.launch {
            try {
                val response = cartRepository.updateQuantity(cartItemId, quantity, context)
                _updateQuantity.value = UpdateQuantityState.Success(
                    message = response.message
                )
            }catch (e: SocketTimeoutException) {
                _updateQuantity.value = UpdateQuantityState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _updateQuantity.value = UpdateQuantityState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private val _updateStatus = MutableStateFlow<UpdateStatusState>(UpdateStatusState.Idle)
    val updateStatus: StateFlow<UpdateStatusState> get() = _updateStatus

    fun updateStatus(cartItemId: Int, status: Boolean, context: Context) {
        _updateStatus.value = UpdateStatusState.Loading
        viewModelScope.launch {
            try {
                val response = cartRepository.updateStatus(cartItemId, status, context)
                _updateStatus.value = UpdateStatusState.Success(
                    message = response.message
                )
            }catch (e: SocketTimeoutException) {
                _updateStatus.value = UpdateStatusState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _updateStatus.value = UpdateStatusState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}