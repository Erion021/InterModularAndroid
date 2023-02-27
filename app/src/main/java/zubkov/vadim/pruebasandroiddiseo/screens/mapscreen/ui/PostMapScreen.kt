package zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PostRouteScreen(navigationController : NavHostController,mapViewModel: MapViewModel,userViewModel: UserViewModel){
    Scaffold(
        topBar = {
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
                            "Publicar Ruta",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        },
    ) {
        content(mapViewModel,navigationController,userViewModel)
    }
}

@Composable
fun content(
    mapViewModel: MapViewModel,
    navigationController: NavHostController,
    userViewModel: UserViewModel
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column{
                Header(mapViewModel,Modifier.fillMaxWidth())
            }
        }
        PostRoute(Modifier.fillMaxWidth(),mapViewModel,userViewModel,navigationController)
    }
}

@Composable
fun Header(mapViewModel: MapViewModel, modifier: Modifier){
    RouteName(modifier = modifier,mapViewModel)
    Difficulty(modifier,mapViewModel)
    Category(modifier,mapViewModel)
    Description(modifier = modifier,mapViewModel)
    PhotoSelector(modifier,onImageSelected = {

    })
}

@Composable
fun RouteName(modifier: Modifier, mapViewModel: MapViewModel){
    var routeName by remember { mutableStateOf("") }
    Text(
        text = "Nombre de la Ruta",
        modifier = modifier.
        padding(start = 65.dp,top= 10.dp)
    )
    OutlinedTextField(
        value = routeName,
        onValueChange = {
            routeName = it
        },
        placeholder = { Text(
            text = "Nombre Ruta",
            color = Color(0xFFDAD3C8)
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDAD3C8),
            unfocusedBorderColor = Color(0xFFDAD3C8)
        ),
        shape = CircleShape,
        modifier = modifier.padding(top = 5.dp, start = 50.dp, end = 50.dp)
    )
    mapViewModel.getRouteName(routeName)
}

@Composable
fun Difficulty(modifier : Modifier,mapViewModel: MapViewModel){
    val difficulties = listOf(
        "Facil",
        "Mediana",
        "Dificil"
    )
    var difficulty by remember { mutableStateOf("") }
    val heightTextField by remember { mutableStateOf(55.dp) }
    var sizeTextField by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(15.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {
        Text(
            text = "Dificultad",
            modifier = Modifier
                .padding(start = 51.dp, bottom = 5.dp),
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp, start = 36.dp)
            ){
                TextField(
                    value = difficulty,
                    onValueChange = {
                        difficulty = it
                        expanded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextField)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            sizeTextField = coordinates.size.toSize()
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {expanded = !expanded}) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }

            mapViewModel.getDifficulty(difficulty)


            AnimatedVisibility(visible = expanded) {
                Card (
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .width(sizeTextField.width.dp),
                    elevation = 10.dp
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                    ) {
                        if(difficulty.isNotEmpty()){
                            items(
                                difficulties.filter {
                                    it.lowercase()
                                        .contains(difficulty.lowercase()) || it.lowercase()
                                        .contains("")
                                }
                                    .sorted()
                            ) {
                                Items(title = it){ title ->
                                    difficulty = title
                                    expanded = false
                                }
                            }
                        } else {
                            items(
                                difficulties.sorted()
                            ) {
                                Items(title = it) { title ->
                                    difficulty = title
                                    expanded = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Category(modifier: Modifier,mapViewModel: MapViewModel){
    val categories = listOf(
        "Escalada",
        "Senderismo",
        "Ciclismo"
    )
    var category by remember { mutableStateOf("") }
    val heightTextField by remember { mutableStateOf(55.dp) }
    var sizeTextField by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(10.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expanded = false
                }
            )
    ) {
        Text(
            text = "Categoria",
            modifier = Modifier
                .padding(start = 51.dp, bottom = 5.dp),
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp, start = 40.dp)
            ){
                TextField(
                    value = category,
                    onValueChange = {
                        category = it
                        expanded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextField)
                        .border(
                            width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            sizeTextField = coordinates.size.toSize()
                        },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {expanded = !expanded}) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }

            mapViewModel.getCategory(category)

            AnimatedVisibility(visible = expanded) {
                Card (
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .width(sizeTextField.width.dp),
                    elevation = 10.dp
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                    ) {
                        if(category.isNotEmpty()){
                            items(
                                categories.filter {
                                    it.lowercase()
                                        .contains(category.lowercase()) || it.lowercase()
                                        .contains("")
                                }
                                    .sorted()
                            ) {
                                Items(title = it){ title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        } else {
                            items(
                                categories.sorted()
                            ) {
                                Items(title = it) { title ->
                                    category = title
                                    expanded = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Items(
    title : String,
    onSelect: (String) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(10.dp)
    ){
        Text(
            text = title
        )
    }
}

@Composable
fun Description(modifier: Modifier,mapViewModel: MapViewModel){
    var description by remember { mutableStateOf("") }
    Text(
        text = "Descripcion",
        modifier = modifier
            .padding(start = 59.dp, top = 5.dp),
    )

    Box(
        modifier = modifier
            .height(100.dp)
            .background(Color.White)
            .padding(start = 50.dp, top = 10.dp, end = 50.dp)
    ) {
        TextField(
            value = description,
            onValueChange = {description = it},
            modifier = Modifier
                .fillMaxSize()
        )
    }
    mapViewModel.getDescription(description)
}

@Composable
fun PhotoSelector(modifier: Modifier,onImageSelected: (List<Bitmap>) -> Unit) {
    val activity = LocalContext.current
    var selectedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val maxSelections = 6

    val selectedBitmaps = remember(selectedUris) {
        mutableStateListOf<Bitmap>().apply {
            selectedUris.forEach { uri ->
                uri.toBitmap(activity.contentResolver)?.let { add(it) }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris ->
        selectedUris = uris.takeIf { it.size <= maxSelections } ?: emptyList()
    }
    Button(
        onClick = { launcher.launch("image/*") },
        modifier = modifier
            .padding(start = 50.dp, end = 50.dp,top = 10.dp)
    ) {
        Text(text = "Selecciona las fotos")
    }

    LazyRow {
        items(selectedBitmaps) { bitmap ->
            Box(modifier = Modifier.padding(8.dp)) {
                if (bitmap != null) {
                    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.size(80.dp))
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color.Black, CircleShape)
                            .clickable { selectedBitmaps.remove(bitmap) }
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }
    }

    DisposableEffect(selectedBitmaps) {
        onDispose {
            onImageSelected(selectedBitmaps.toList())
        }
    }
}

fun Uri.toBitmap(contentResolver: ContentResolver): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(this)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        null
    }
}

@Composable
fun PostRoute(modifier : Modifier,mapViewModel: MapViewModel,userViewModel: UserViewModel,navigationController: NavHostController){
    Button(
        onClick = {
            mapViewModel.MapRecordLatLng(navigationController,userViewModel)
        },
        modifier = modifier
            .padding(start = 50.dp, end = 50.dp, top = 30.dp, bottom = 20.dp)
            .width(100.dp)
            .height(50.dp)
            .clip(shape = RoundedCornerShape(24.dp))
    ) {
        Text(
            "Subir Ruta"
        )
    }
}
