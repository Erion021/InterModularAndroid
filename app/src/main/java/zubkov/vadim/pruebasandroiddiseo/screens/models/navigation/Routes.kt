package zubkov.vadim.pruebasandroiddiseo.screens.models.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splashscreen")
    object Login: Routes("login")
    object Register: Routes("register")
    object RegisterMiddle: Routes("registerMiddle")
    object RegisterLast: Routes("registerLast")
    object Home: Routes("home")
    object GmapScreen: Routes("gmap")
    object GmapPost: Routes("gmap_view")
    object Person: Routes("users")
    object PersonDetail: Routes("usersDetail")
}
