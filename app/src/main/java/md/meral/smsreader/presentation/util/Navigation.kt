package md.meral.smsreader.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import md.meral.smsreader.presentation.home.HomeScreen
import md.meral.smsreader.presentation.permission.PermissionScreen
import md.meral.smsreader.presentation.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.PermissionScreen.route) {
            PermissionScreen(navController = navController)
        }
    }
}