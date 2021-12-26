package md.meral.smsreader.presentation.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object HomeScreen: Screen("home_screen")
    object PermissionScreen: Screen("permission_screen")
}