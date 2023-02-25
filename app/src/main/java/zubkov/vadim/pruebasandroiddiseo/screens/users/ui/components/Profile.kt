package zubkov.vadim.pruebasandroiddiseo.screens.users.components

import android.view.Menu
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.Components.MainCard
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel


@ExperimentalFoundationApi
@Composable
fun Profile(navigationController: NavHostController, user:PersonDTO,
            mapViewModel: MapViewModel,menuViewModel: MenuViewModel,
            userViewModel: UserViewModel,personViewModel: PersonViewModel,
            fullScreen:Boolean = false
){
        personViewModel.returnPerson(userViewModel)
    menuViewModel.devolverListaByEmail(user.email)
    var loggedUser = personViewModel.person.value!!.first()
        var followers = personViewModel.followers(user.email)
        var selectedTabIndex by remember {
            mutableStateOf(0)
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            Header(
                name = user.nick,
                fullScreen,
                navigationController
            )
            Spacer(modifier = Modifier.height(4.dp))
            ProfileSection(menuViewModel.routesListByEmail.value!!.size,followers.size,user.following.size)
            Spacer(modifier = Modifier.height(25.dp))
            ButtonSection(
                navigationController,
                modifier = Modifier.fillMaxWidth(),
                user,
                loggedUser,
                personViewModel
            )
            Spacer(modifier = Modifier.height(25.dp))

            PostTabView(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp),
                imageWithTexts = listOf(
                    ImageWithText(
                        image = painterResource(id = R.drawable.rutas),
                        text = "Rutas"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.corazon),
                        text = "Likes"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.seguidos),
                        text = "Seguidos"
                    ),
                    ImageWithText(
                        image = painterResource(id = R.drawable.seguidores),
                        text = "Seguidores"
                    ),
                )
            ) {
                selectedTabIndex = it
            }

            when (selectedTabIndex) {

                0 ->
                    MostrarRutasCreadas(
                        mapViewModel,
                        menuViewModel,
                        navigationController,
                        user,
                    )
                1 -> MostrarRutas(
                    rutasLikeUser(user,menuViewModel.routesList.value!!),
                    modifier = Modifier.fillMaxWidth(),
                    navigationController,
                    mapViewModel,
                    user,
                    menuViewModel
                )
                2 -> MostrarUsuarios(
                    user.following,
                    modifier = Modifier.fillMaxWidth(),
                    navigationController
                )
                3 -> MostrarUsuarios(
                    followers,
                    modifier = Modifier.fillMaxWidth(),
                    navigationController
                )
            }


        }
    }


fun rutasLikeUser(user: PersonDTO,listaRutas:List<MenuDTO>):List<MenuDTO>
{
    return listaRutas.filter{ruta-> ruta._id in user.fav_routes}
}

@Composable
fun Header(
    name: String,
    mostrarAtras : Boolean,
    navigationController: NavHostController,
) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            if(mostrarAtras) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            navigationController.navigateUp()
                        }
                )
            }

            Text(
                text = name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
    }
}

@Composable
fun ProfileSection(
    rutas:Int,seguidores:Int,seguidos:Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            RoundImage(
                image = painterResource(id = R.drawable.fotoperfil),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f),rutas,seguidores,seguidos)
        }
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier,rutas:Int,seguidores:Int,siguiendo:Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(numberText = rutas.toString() , text = "Rutas")
        ProfileStat(numberText = siguiendo.toString(), text = "Siguiendo")
        ProfileStat(numberText = seguidores.toString() , text = "Seguidores")
    }
}

@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ButtonSection(
    navigationController: NavHostController,
    modifier: Modifier = Modifier,
    user:PersonDTO,
    loggedUser:PersonDTO,
    personViewModel: PersonViewModel
) {
    val minWidth = 175.dp
    val height = 35.dp
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        BotonDetalleUser(
            icon = Icons.Outlined.Person,
            text = "Datos  ",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height),
            navigationController = navigationController,
            user = user
        )
        BotonSeguirUser(
            Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height),
            user,
            loggedUser,
            personViewModel
        )

    }
}


@Composable
fun BotonDetalleUser(
modifier: Modifier = Modifier,
text: String? = null,
icon: ImageVector? = null,
navigationController: NavHostController,
user:PersonDTO
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                navigationController.navigate("usersDetail/${user.email}")
            }
            .background(MaterialTheme.colors.background, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.5.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(6.dp)
    ) {
        if(text != null) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
        if(icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@Composable
fun BotonSeguirUser(
    modifier: Modifier = Modifier,
    user:PersonDTO,
    loggedUser:PersonDTO,
    personViewModel: PersonViewModel
) {
    if (user.email != loggedUser.email) {
        var siguiendo by remember {mutableStateOf(user.email in loggedUser.following)}
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable {
                    if(siguiendo){
                        personViewModel.unfollowUser(loggedUser.email,user.email)
                    }else{
                        personViewModel.followUser(loggedUser.email,user.email)
                    }
                    siguiendo = !siguiendo
                }
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(6.dp)
        ) {
            Text(
                text = if (siguiendo) "Dejar de seguir  " else "Seguir  ",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Icon(
                imageVector = if (siguiendo) Icons.Filled.Close else Icons.Filled.Check,
                contentDescription = null,
                tint = if (siguiendo) Color.Red else Color.Green
            )
        }
    }
}

@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithTexts: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val inactiveColor = Color(0xFF777777)
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primaryVariant,
        modifier = modifier
    ) {
        imageWithTexts.forEachIndexed { index, item ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color.Black,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Icon(
                    painter = item.image,
                    contentDescription = item.text,
                    tint = if(selectedTabIndex == index) Color.Black else inactiveColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(20.dp)
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MostrarRutasCreadas(
    mapViewModel: MapViewModel,
    menuViewModel: MenuViewModel,
    navigationController: NavHostController,
    user: PersonDTO,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1E6E1))
    ) {
        items(menuViewModel.routesListByEmail.value!!.size) { person ->
            val route = menuViewModel.routesListByEmail.value!![person]
            MainCard(
                ruta = route,
                mapViewModel = mapViewModel,
                navigationController = navigationController,
                user = user,
                menuViewModel = menuViewModel,
                onItemClicked = { card ->
                    menuViewModel.updateActualRoute(card)
                    navigationController.navigate(Routes.RouteDetail.route)
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MostrarRutas(
    listaRutas: List<MenuDTO>,
    modifier: Modifier = Modifier,
    navigationController: NavHostController,
    mapViewModel: MapViewModel,
    user:PersonDTO,
    menuViewModel: MenuViewModel
) {
    LazyColumn(
        modifier = modifier
            .scale(1.01f)
            .fillMaxSize()
    ) {
        items(listaRutas) { ruta ->
            MainCard(ruta, mapViewModel, navigationController,user,menuViewModel,true,
                onItemClicked = { card ->
                    menuViewModel.updateActualRoute(card)
                    navigationController.navigate(Routes.RouteDetail.route)
                })
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MostrarUsuarios(
    users: List<String>,
    modifier: Modifier = Modifier,
    navigationController: NavHostController
) {
    LazyColumn(
        modifier = modifier
            .scale(1.01f)
            .fillMaxHeight()
    ) {
        items(users) {
            CardUsuario(it,navigationController)
        }
    }
}

data class ImageWithText(
    val image: Painter,
    val text: String
)