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
    object DetailProduct : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object DetailCategory : Screen("category_detail/{categoryId}") {
        fun createRoute(categoryId: Int) = "category_detail/$categoryId"
    }
    object MyCart: Screen("myCart")
    object Checkout: Screen("checkout")
    object Order: Screen("order")
}