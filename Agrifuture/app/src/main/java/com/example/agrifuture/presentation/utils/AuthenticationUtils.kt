package com.example.agrifuture.presentation.utils

import android.content.Context

class AuthenticationUtils(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE)

    fun isLoginComplete(): Boolean {
        return sharedPreferences.getBoolean(getToken(), false)
    }

    fun setLoginComplete() {
        sharedPreferences.edit()
            .putBoolean(getToken(), true)
            .apply()
    }

    fun setLogout() {
        sharedPreferences.edit()
            .remove(getToken())
            .apply()
    }

    fun setToken(token: String) {
        sharedPreferences.edit()
            .putString("token", token)
            .apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }


}