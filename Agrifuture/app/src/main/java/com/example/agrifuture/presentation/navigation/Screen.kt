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
    object DetailProduct : Screen("product_detail/{id}") {
        fun createRoute(id: Int) = "product_detail/$id"
    }
    object DetailCategory : Screen("category_detail/{id}") {
        fun createRoute(id: Int) = "category_detail/$id"
    }
    object MyCart: Screen("myCart")
    object Checkout: Screen("checkout")
    object Order: Screen("order")
}