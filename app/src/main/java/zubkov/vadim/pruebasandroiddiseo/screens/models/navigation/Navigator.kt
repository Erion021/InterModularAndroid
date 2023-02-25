package zubkov.vadim.pruebasandroiddiseo.screens.models.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import zubkov.vadim.pruebasandroiddiseo.Components.CardExtended
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.Menu
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.login.Login
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.GoogleMapsView
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.MapaGoogle
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.PostRouteScreen
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.Components._id
import zubkov.vadim.pruebasandroiddiseo.screens.register.RegisterScreen
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel.RegisterViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.views.RegisterLastScreen
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.views.RegisterMiddleScreen
import zubkov.vadim.pruebasandroiddiseo.screens.splashscreen.SplashScreen
import zubkov.vadim.pruebasandroiddiseo.screens.users.PersonScreen
import zubkov.vadim.pruebasandroiddiseo.screens.users.ProfileDetail
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.EditUserScreen
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.ExternalUserScreen
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel
import javax.inject.Singleton

@Composable
fun CustomNavigator(
    userViewModel: UserViewModel,
    mapViewModel : MapViewModel,
    registerViewModel : RegisterViewModel,
    menuViewModel: MenuViewModel,
    personViewModel : PersonViewModel
) {
    @Singleton
    val navigationController = rememberNavController()

    menuViewModel.devolverLista()
    menuViewModel.devolverComentariosRuta(_id)
    menuViewModel.devolverListaByEmail("")
    NavHost(navController = navigationController, startDestination = Routes.SplashScreen.route) {
        composable(route = Routes.SplashScreen.route){
            SplashScreen(navigationController = navigationController)
        }
        composable(route = Routes.Login.route) {
            Login(navigationController = navigationController, userViewModel = userViewModel)
        }
        composable(route = Routes.Register.route) {
            RegisterScreen(navigationController = navigationController, registerViewModel = registerViewModel)
        }
        composable(route = Routes.RegisterMiddle.route) {
            RegisterMiddleScreen(navigationController = navigationController, registerViewModel = registerViewModel)
        }
        composable(route = Routes.RegisterLast.route) {
            RegisterLastScreen(navigationController = navigationController, registerViewModel = registerViewModel)
        }
        composable(route = Routes.Home.route) {
            Menu(navigationController = navigationController, menuViewModel = menuViewModel,mapViewModel = mapViewModel,
                personViewModel = personViewModel, userViewModel = userViewModel
            )
        }
        composable(route = Routes.RouteDetail.route) {
            CardExtended(navigationController = navigationController,menuViewModel,userViewModel,
                _id,mapViewModel)
        }
        composable(route = Routes.GmapScreen.route) {
            MapaGoogle(navigationController = navigationController, mapViewModel = mapViewModel)
        }
        composable(route = Routes.GmapView.route) {
            GoogleMapsView(navigationController = navigationController, mapViewModel = mapViewModel)
        }
        composable(route = Routes.GmapPost.route) {
            PostRouteScreen(navigationController = navigationController, mapViewModel = mapViewModel, userViewModel = userViewModel)
        }
        composable(route = Routes.Person.route) {
            PersonScreen(navigationController = navigationController,personViewModel,userViewModel,mapViewModel,menuViewModel)
        }
        composable(route = Routes.ExtPerson.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })) {
            ExternalUserScreen(navigationController = navigationController,personViewModel,userViewModel,mapViewModel,menuViewModel,it.arguments?.getString("email") ?: "")
        }
        composable(route = Routes.PersonDetail.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })) {
            ProfileDetail(navigationController = navigationController,personViewModel,userViewModel,it.arguments?.getString("email") ?: "")
        }
        composable(route = Routes.ModifyUser.route) {
            EditUserScreen(navigationController = navigationController,personViewModel,userViewModel)
        }
    }
}