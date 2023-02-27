package zubkov.vadim.pruebasandroiddiseo.screens.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes

@Composable
fun TopBarContent(){
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "SENDERO SUR",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Composable
fun BottomBarContent(navigationController:NavHostController)
{
    BottomAppBar(
        modifier = Modifier
            .height(65.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        cutoutShape = CircleShape,
        elevation = 22.dp
    ) {
        CustomBarContent(navigationController)
    }
}


@Composable
fun CustomBarContent(navigationController: NavHostController) {
    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BottomNavigation(
        modifier = Modifier
            .padding(12.dp, 0.dp, 12.dp, 0.dp)
            .height(100.dp),
        elevation = 0.dp
    ) {

        BottomNavigationItem(
            icon = {Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home",
                modifier = Modifier.size(35.dp),
            )},
            label = {Text(text = "Rutas")},
            selected = currentRoute?.hierarchy?.any { it.route == Routes.Home.route } == true,
            selectedContentColor = Color(zubkov.vadim.pruebasandroiddiseo.R.color.purple_700),
            unselectedContentColor = Color.White.copy(alpha = 0.4f),
            onClick = {navigationController.navigate(Routes.Home.route)  }
        )

        BottomNavigationItem(
            icon = {Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "profile",
                modifier = Modifier.size(35.dp),
            )},
            label = {Text(text = "Perfil",)},
            selected = currentRoute?.hierarchy?.any { it.route == Routes.Person.route } == true,
            selectedContentColor = Color(zubkov.vadim.pruebasandroiddiseo.R.color.purple_700),
            unselectedContentColor = Color.White.copy(alpha = 0.4f),
            onClick = {navigationController.navigate(Routes.Person.route)  }
        )
    }
}
