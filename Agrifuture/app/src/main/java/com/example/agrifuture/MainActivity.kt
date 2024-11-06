package com.example.agrifuture

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agrifuture.presentation.navigation.Screen
import com.example.agrifuture.presentation.repository.CustomerRepository
import com.example.agrifuture.presentation.utils.AuthenticationUtils
import com.example.agrifuture.presentation.utils.OnboardingUtils
import com.example.agrifuture.presentation.view.HomeScreen
import com.example.agrifuture.presentation.view.LoginScreen
import com.example.agrifuture.presentation.view.NotificationScreen
import com.example.agrifuture.presentation.view.OnboardingScreen
import com.example.agrifuture.presentation.view.ProfileScreen
import com.example.agrifuture.presentation.view.RecommendationScreen
import com.example.agrifuture.presentation.view.RegisterScreen
import com.example.agrifuture.presentation.view.ShopScreen
import com.example.agrifuture.presentation.viewModel.LoginVM
import com.example.agrifuture.presentation.viewModel.RegisterVM
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
                val authRepo = CustomerRepository()
                val context = LocalContext.current
                val loginVM by lazy { LoginVM(authRepo, context) }
                LoginScreen(loginVM = loginVM, navController = navController)
            }

            composable(Screen.Register.route){
                val customerRepo = CustomerRepository()
                val registerVM = RegisterVM(customerRepo)
                RegisterScreen(navController = navController, registerVM = registerVM)
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
                ProfileScreen(navController = navController)
            }
        }
    }
}

