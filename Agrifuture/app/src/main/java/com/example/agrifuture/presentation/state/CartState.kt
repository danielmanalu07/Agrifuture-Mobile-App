package com.example.agrifuture.presentation.state

import com.example.agrifuture.presentation.model.Cart_Items
import com.example.agrifuture.presentation.model.Carts

sealed class CartState {
    object Idle: CartState()
    object Loading: CartState()
    data class Success(
        val message: String,
        val item: Carts
    ) : CartState()
    data class Error(val message: String): CartState()
}

sealed class MyCartState{
    object Idle: MyCartState()
    object Loading: MyCartState()
    data class Success(
        val message: String,
        val items: List<Cart_Items>
    ) : MyCartState()
    data class Error(val message: String): MyCartState()
}

sealed class UpdateQuantityState{
    object Idle: UpdateQuantityState()
    object Loading: UpdateQuantityState()
    data class Success(
        val message: String
    ) : UpdateQuantityState()
    data class Error(
        val message: String
    ) : UpdateQuantityState()
}

sealed class UpdateStatusState{
    object Idle: UpdateStatusState()
    object Loading: UpdateStatusState()
    data class Success(
        val message: String
    ) : UpdateStatusState()
    data class Error(
        val message: String
    ) : UpdateStatusState()
}