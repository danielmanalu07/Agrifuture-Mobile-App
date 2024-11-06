package com.example.agrifuture.presentation.utils

import android.content.Context

class OnboardingUtils(private val context: Context) {

    fun isOnboardingComplete(): Boolean{
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false)
    }

    fun setOnboardingComplete() {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("completed", true)
            .apply()
    }
}