package com.example.agrifuture.presentation.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agrifuture.presentation.model.Order
import com.example.agrifuture.presentation.model.Orders
import com.example.agrifuture.presentation.repository.OrderRepository
import com.example.agrifuture.presentation.state.CartState
import com.example.agrifuture.presentation.state.MyOrderState
import com.example.agrifuture.presentation.state.OrderState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class OrderVM: ViewModel() {
    val orderRepository = OrderRepository()

    fun getOrders() : List<Order>{
        return orderRepository.getOrders()
    }

    private val _orderState = MutableStateFlow<OrderState>(OrderState.Idle)
    val orderState: StateFlow<OrderState> get() = _orderState

    fun addToOrder(context: Context) {
        _orderState.value = OrderState.Loading
        viewModelScope.launch {
            try {
                val response = orderRepository.addToOrder(context)
                _orderState.value = OrderState.Success(
                    messsage = response.message
                )
            } catch (e: SocketTimeoutException) {
                _orderState.value = OrderState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _orderState.value = OrderState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private val _myOrder = MutableStateFlow<MyOrderState>(MyOrderState.Idle)
    val myorder: StateFlow<MyOrderState> get() = _myOrder

    fun myOrder(context: Context) {
        _myOrder.value = MyOrderState.Loading
        viewModelScope.launch {
            try {
                val response = orderRepository.myOrder(context)
                _myOrder.value = MyOrderState.Success(
                    messsage = response.message,
                    items = response.items
                )
            }catch (e: SocketTimeoutException) {
                _myOrder.value = MyOrderState.Error("Permintaan timeout, coba lagi nanti.")
            } catch (e: Exception) {
                _myOrder.value = MyOrderState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    private val _orderList = MutableStateFlow<List<Orders>>(emptyList())
    val orderList: StateFlow<List<Orders>> = _orderList

    fun myOrderPending(context: Context) {
        viewModelScope.launch {
            try {
                val data = orderRepository.myOrderPending(context)
                _orderList.value = listOf(data)
            } catch (e: Exception) {
                Log.e("Orders", "Error fetching order: ${e.localizedMessage}", e)
            }
        }
    }


}