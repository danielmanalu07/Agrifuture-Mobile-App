package com.example.agrifuture.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home: Screen("home")
    object Shop: Screen("shop")
    object Recommendation: Screen("recommendation")
    object Notification: Screen("notification")
    object Profile: Screen("profile")
}