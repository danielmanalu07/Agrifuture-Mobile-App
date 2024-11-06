package com.example.agrifuture.presentation.utils

import android.content.Context

class AuthenticationUtils(private val context: Context) {
    fun isLoginComplete(): Boolean{
        return context.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getBoolean("completed", false)
    }

    fun setLoginComplete() {
        return context.getSharedPreferences("login", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)
            .apply()
    }

    fun clearLoginComplete() {
        context.getSharedPreferences("login", Context.MODE_PRIVATE)
            .edit()
            .remove("completed")
            .apply()
    }
}