package com.example.testapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testapp.presentation.screen.DialPadScreen
import com.example.testapp.presentation.screen.SplashScreen

object AppDestinations {
    const val SPLASH_SCREEN = "splash"
    const val DIAL_PAD_SCREEN = "dialPad"
    // Add other routes here
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPLASH_SCREEN // Your initial screen
    ) {
        composable(AppDestinations.SPLASH_SCREEN) {
            SplashScreen(
                onAnimationFinished = {
                    navController.navigate(AppDestinations.DIAL_PAD_SCREEN) {
                        popUpTo(AppDestinations.SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppDestinations.DIAL_PAD_SCREEN) {
            DialPadScreen()
        }
    }
}