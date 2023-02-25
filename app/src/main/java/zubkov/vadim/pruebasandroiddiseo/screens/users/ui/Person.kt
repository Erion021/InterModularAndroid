package zubkov.vadim.pruebasandroiddiseo.screens.users

import android.util.Log
import android.view.Menu
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.BottomBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.TopBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.users.components.Profile
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonScreen(navigationController: NavHostController,personViewModel: PersonViewModel,userViewModel: UserViewModel
,mapViewModel:MapViewModel,menuViewModel:MenuViewModel) {
    personViewModel.returnPerson(userViewModel)
    Log.d("EMAIL EN PANTALLA USER","${userViewModel.email.value}")
    Scaffold(
        topBar = { TopBarContent() },
        bottomBar = { BottomBarContent(navigationController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    navigationController.navigate(Routes.GmapScreen.route)
                },
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add ruta")
            }
        }
    ) {
        val user = personViewModel.person.value!!.first()
        Log.d("NAME","${user.name}")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE1E6E1))
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Profile(
                navigationController,
                user,
                mapViewModel,
                menuViewModel,
                userViewModel,
                personViewModel
            )
        }
    }
}