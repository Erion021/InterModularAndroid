package zubkov.vadim.pruebasandroiddiseo.Components

import android.Manifest
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO

import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.Components.CorazonFavorito
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.Components._id

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardExtended(navigationController: NavHostController,menuViewModel: MenuViewModel,userViewModel: UserViewModel,idPublication : String,mapViewModel: MapViewModel){

    menuViewModel.devolverComentariosRuta(_id)
    val route = menuViewModel.actualRoute.value!!
    Log.d("ID",idPublication)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = route.name,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navigationController.navigateUp()
                            },
                        tint = MaterialTheme.colors.background
                    )
                },

                actions = {
                    //if (!tarjetaDeUsuario) {
                        CorazonFavorito()
                    /*
                        else{
                        val interactionSource = MutableInteractionSource()
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "favorito",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .scale(scale = 1f)
                                .size(size = 24.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { /*cardViewModel.openConfirmarBorrado()*/ }
                        )
                    }
                     */
                }
            )
        },

        content = {
            CardExtendedComp(navigationController,route,menuViewModel, userViewModel, idPublication, mapViewModel)
        }
    )
}



@Composable
fun CardExtendedComp(navigationController: NavHostController,ruta:MenuDTO,menuViewModel: MenuViewModel,userViewModel: UserViewModel,idPublication : String,mapViewModel: MapViewModel)
{
    val scrollState = rememberScrollState()
    val lazyScrollState = rememberLazyListState()
    val photos = listOf(
        "http://10.0.2.2:8080/publicationPicture/${ruta._id}/${ruta._id}_0.jpg",
        "http://10.0.2.2:8080/publicationPicture/${ruta._id}/${ruta._id}_1.jpg",
        "http://10.0.2.2:8080/publicationPicture/${ruta._id}/${ruta._id}_2.jpg",
        "http://10.0.2.2:8080/publicationPicture/${ruta._id}/${ruta._id}_3.jpg"
    )

    Column(
        Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.76f)
                .verticalScroll(scrollState)
        ) {
            Carousel(photos)
            Spacer(modifier = Modifier.height(16.dp))
            CardInfo(navigationController,ruta,menuViewModel, userViewModel, idPublication, mapViewModel)
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxSize(),
            state = lazyScrollState
        ) {
            if (menuViewModel.commentList.value!!.isEmpty()){
                item{
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "¡Anímate a dejar un comentario!",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } else {
                items(menuViewModel.commentList.value!!.size) { person ->
                    val comment = menuViewModel.commentList.value!![person]
                    CommentsSection(navigationController, comment)
                }
            }
        }

        LaunchedEffect(lazyScrollState) {
            scrollState.scrollTo(lazyScrollState.firstVisibleItemScrollOffset)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)
@Composable
fun Carousel(imagenes:List<String>) {
    val slideImage = remember { mutableStateOf(imagenes.get(0)) }
    val pagerState = rememberPagerState(0)
    HorizontalPager(count = imagenes.size, state = pagerState) { page ->
        when(page)  {
            else -> slideImage.value = imagenes.get(page)
        }
        val painter = rememberAsyncImagePainter(model = imagenes.get(page))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(346.dp),
                painter = painter,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }

    Spacer(modifier = Modifier.padding(4.dp))

    DotsIndicator(
        totalDots = imagenes.size,
        selectedIndex = pagerState.currentPage,
        selectedColor = MaterialTheme.colors.primaryVariant,
        unSelectedColor = Color.Gray
    )
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun CardInfo(navigationController: NavHostController, ruta: MenuDTO,menuViewModel: MenuViewModel,userViewModel: UserViewModel,idPublication : String,mapViewModel: MapViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            HeaderCard(navigationController,ruta)
            MidCard(ruta)
            BottomCard(ruta, menuViewModel = menuViewModel, userViewModel = userViewModel, idPublication = idPublication,mapViewModel = mapViewModel)
        }
    }
}

@Composable
fun HeaderCard(navigationController: NavHostController,ruta:MenuDTO){

    Spacer(modifier = Modifier.height(8.dp))

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 0.dp)) {

        Box(modifier = Modifier.padding(0.dp),) {
            ImagenUsuario(navigationController,ruta)
        }

        Text(
            text = ruta.email,
            modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            style = MaterialTheme.typography.caption,

            )

        Text(
            text = "   |   " + ruta.date,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
            color = Color.Black,
            style = MaterialTheme.typography.overline
        )

    }

}

@Composable
fun MidCard(ruta:MenuDTO){
    Spacer(modifier = Modifier.height(24.dp))
    Title("Descripción")
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = ruta.description,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        color = Color.Black,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Start
    )
}

@Composable
fun BottomCard(ruta:MenuDTO,menuViewModel: MenuViewModel,userViewModel: UserViewModel,idPublication : String,mapViewModel: MapViewModel){
    val message: String by menuViewModel.message.observeAsState(initial = "")

    Spacer(modifier = Modifier.height(24.dp))
    Title(title = "Info Ruta")
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Column() {
            MiniInfoCard(title = "Distancia", value = "${ruta.distance} m")
            Spacer(modifier = Modifier.padding(10.dp))
            MiniInfoCard(title = "Dificultad", value = ruta.difficulty)
        }
        Spacer(modifier = Modifier.padding(30.dp))
        Column() {
            MiniInfoCard(title = "Actividad", value = ruta.category)
            Spacer(modifier = Modifier.padding(10.dp))
            MiniInfoCard(title = "Duracion", value = "${ruta.duration}")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    GoogleMaps(ruta = ruta, mapViewModel = mapViewModel)
    Spacer(modifier = Modifier.height(16.dp))
    PostComment(
        Modifier
            .fillMaxWidth(),
        menuViewModel = menuViewModel,
        userViewModel = userViewModel,
        idPublication = idPublication,
        message = message,
        onTextChanged = {
            menuViewModel.onMessageChange(it)
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GoogleMaps(ruta:MenuDTO,mapViewModel : MapViewModel){
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
    ){
        val locationPermissionState = rememberPermissionState(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val middlePoint = LatLng((ruta.lat_A + ruta.lng_A) / 2, (ruta.lat_B + ruta.lng_B) / 2)
        val cameraPosition = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(LatLng(ruta.lat_A , ruta.lng_A),10f)
        }

        if (locationPermissionState.status.isGranted){
            GoogleMap(
                properties = mapViewModel.properties,
                cameraPositionState = cameraPosition
            ){
                //Position A
                Marker(
                    state = MarkerState(
                        position = LatLng(ruta.lat_A,ruta.lng_A)
                    )
                )

                //Position B
                Marker(
                    state = MarkerState(
                        position = LatLng(ruta.lat_B,ruta.lng_B)
                    )
                )

                Polyline(
                    points = ruta.rec_movement
                )
            }
        } else {
            LaunchedEffect(locationPermissionState.status.isGranted){
                locationPermissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = Color(0xFFF5CAC9),
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ImagenUsuario(navigationController: NavHostController,ruta:MenuDTO) {
    val urlProfile = "http://10.0.2.2:8080/profilePicture/${ruta.email}.jpg"

    val painterProfile = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = urlProfile).apply(block = fun ImageRequest.Builder.() {
            error(R.drawable.fotoperfil)
        }).build()
    )

    Image(
        painter = painterProfile,
        contentDescription = "Imagen",
        modifier = Modifier
            .size(40.dp)
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable { /*navigationController.navigate("user/${ruta.usuarioPublicado.id}/true")*/ }
    )
}

@Composable
fun MiniInfoCard(title: String, value: String) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.primary)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = value,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1E3054),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CommentsSection(navigationController: NavHostController,comment : CommentDTO){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LoadComments(Modifier
            .fillMaxWidth(),
            navigationController,
            comment
        )
    }
}

@Composable
fun PostComment(modifier : Modifier, menuViewModel: MenuViewModel, userViewModel: UserViewModel, idPublication : String, onTextChanged : (String) -> Unit, message : String){
    Row(
        modifier = modifier
    ){
        TextField(
            value = message,
            onValueChange = {
                onTextChanged(it)
            },
            label = {
                Text("Comenta")
            },
            modifier = Modifier
                .width(400.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            menuViewModel.postComment(userViewModel,idPublication)
                        }
                )
            },
            singleLine = true
        )
    }
}

@Composable
fun LoadComments(modifier: Modifier,navigationController: NavHostController, ruta : CommentDTO){
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.primaryVariant)
            .fillMaxWidth()
    ){
        cardComments(text = ruta.message, modifier = Modifier.fillMaxWidth(),ruta,navigationController)
    }
}

@Composable
fun cardComments(text : String,modifier : Modifier, comment : CommentDTO,navigationController: NavHostController){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 8.dp)) {
            val urlProfile = "http://10.0.2.2:8080/profilePicture/${comment.email}.jpg"

            val painterProfile = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = urlProfile).apply(block = fun ImageRequest.Builder.() {
                    error(R.drawable.fotoperfil)
                }).build()
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterProfile,
                    contentDescription = "Foto Perfil Usuario",
                    modifier = Modifier
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { navigationController.navigate("extUsers/${comment.email}") }
                )
                Text(
                    text = comment.date,
                    color = Color.Black,
                    style = MaterialTheme.typography.caption,
                )
            }

            Spacer(Modifier.padding(start = 5.dp))
            Text(
                text = text,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                color = Color.Black,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}