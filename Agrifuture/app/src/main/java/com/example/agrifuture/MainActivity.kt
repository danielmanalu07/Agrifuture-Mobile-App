package com.example.agrifuture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.AuthRepository
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.example.agrifuture.presentation.utils.OnboardingUtils
import com.example.agrifuture.presentation.view.CategoryDetailScreen
import com.example.agrifuture.presentation.view.CheckoutScreen
import com.example.agrifuture.presentation.view.HomeScreen
import com.example.agrifuture.presentation.view.LoginScreen
import com.example.agrifuture.presentation.view.MyCartScreen
import com.example.agrifuture.presentation.view.NotificationScreen
import com.example.agrifuture.presentation.view.OnboardingScreen
import com.example.agrifuture.presentation.view.OrderScreen
import com.example.agrifuture.presentation.view.ProductDetailScreen
import com.example.agrifuture.presentation.view.ProfileScreen
import com.example.agrifuture.presentation.view.RecommendationScreen
import com.example.agrifuture.presentation.view.RegisterScreen
import com.example.agrifuture.presentation.view.ShopScreen
import com.example.agrifuture.presentation.viewModel.AuthVM
import com.example.agrifuture.presentation.viewModel.CartVM
import com.example.agrifuture.presentation.viewModel.CheckoutVM
import com.example.agrifuture.presentation.viewModel.OrderVM
import com.example.agrifuture.ui.theme.AgrifutureTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            AgrifutureTheme {
               Surface (color = MaterialTheme.colorScheme.background) {
                   MainNavigation()
               }
            }
        }
    }

    private val onboardingUtils by lazy { OnboardingUtils(this) }
    private val authenticationUtils by lazy { AuthenticationUtils(this) }

    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        val startPage = if (onboardingUtils.isOnboardingComplete()){
            if (authenticationUtils.isLoginComplete()){
                Screen.Home.route
            } else{
                Screen.Login.route
            }
        } else {
            Screen.Onboarding.route
        }


        NavHost(
            navController = navController,
            startDestination = startPage,
        ) {
            composable(Screen.Onboarding.route){
                OnboardingScreen(
                    navController = navController,
                    onFinished ={
                        onboardingUtils.setOnboardingComplete()
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Login.route){
                val authRepository = AuthRepository()
                val authVM = AuthVM(authRepository, listOf(), navController)
                LoginScreen(loginVM = authVM, navController = navController)
            }

            composable(Screen.Register.route){
                val authRepository = AuthRepository()
                val authVM = AuthVM(authRepository, listOf(), navController)
                RegisterScreen(navController = navController, authVM = authVM)
            }

            composable(Screen.Home.route){
                HomeScreen(navController = navController)
            }

            composable(Screen.Shop.route){
                ShopScreen(navController = navController)
            }

            composable(Screen.Recommendation.route){
                RecommendationScreen(navController = navController)
            }

            composable(Screen.Notification.route){
                NotificationScreen(navController = navController)
            }

            composable(Screen.Profile.route){
                val repository = AuthRepository()
                val authVM = AuthVM(navController = navController, repository = repository, customer = listOf())
                ProfileScreen(navController = navController, authVM)
            }

            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId")
                productId?.let {
                    ProductDetailScreen(navController = navController, productId = it)
                }
            }

            composable(
                route = Screen.DetailCategory.route,
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId")
                categoryId?.let {
                    CategoryDetailScreen(navController = navController, categoryId = it)
                }
            }

            composable(Screen.MyCart.route){
                val cartVM = CartVM()
                MyCartScreen(navController = navController, cartVM = cartVM)
            }

            composable(Screen.Checkout.route){
                val checkoutVM = CheckoutVM()
                CheckoutScreen(checkoutVM = checkoutVM, navController = navController)
            }
            composable(Screen.Order.route) {
                val orderVM = OrderVM()
                OrderScreen(orderVM = orderVM, navController = navController)
            }
        }
    }
}

