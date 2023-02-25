package zubkov.vadim.pruebasandroiddiseo.screens.models.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splashscreen")
    object Login: Routes("login")
    object Register: Routes("register")
    object RegisterMiddle: Routes("registerMiddle")
    object RegisterLast: Routes("registerLast")
    object Home: Routes("home")
    object RouteDetail: Routes("route")
    object GmapScreen: Routes("gmap")
    object GmapView: Routes("gmap_view")
    object GmapPost: Routes("gmap_post")
    object Person: Routes("users")
    object ExtPerson: Routes("extUsers/{email}")
    object PersonDetail: Routes("usersDetail/{email}")
    object ModifyUser: Routes("modifyUser")
}
