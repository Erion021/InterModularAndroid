package zubkov.vadim.pruebasandroiddiseo.screens.users.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.users.components.Profile
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExternalUserScreen(navigationController: NavHostController, personViewModel: PersonViewModel,
                       userViewModel: UserViewModel, mapViewModel: MapViewModel, menuViewModel: MenuViewModel,
                        email:String
) {
    var user = personViewModel.returnPerson(email)
    if(user != null) {
        Column() {
            Profile(
                navigationController,
                user,
                mapViewModel,
                menuViewModel,
                userViewModel,
                personViewModel,
                true,
                false
            )
        }
    }else{
        Text("Cargando....")
    }
}