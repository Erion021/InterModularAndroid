package zubkov.vadim.pruebasandroiddiseo.screens.users.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.descriptionOld
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.lastnameOld
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.nameOld

@Composable
fun EditUserScreen(navigationController: NavHostController, personViewModel: PersonViewModel,userViewModel: UserViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Modificar Perfil",
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        color  =Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navigationController.navigateUp()
                            },
                        tint = Color.Black
                    )
                }
            )
        },

        content = {
            ModificarUsuario(navigationController,personViewModel,userViewModel)
        }
    )


}

@Composable
fun ModificarUsuario(navigationController: NavHostController,personViewModel: PersonViewModel,userViewModel: UserViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        var Name by remember {
            mutableStateOf("")
        }

        var LastName by remember {
            mutableStateOf("")
        }


        var Description by remember {
            mutableStateOf("")
        }

        if (Name.isEmpty()){
            Log.d("LOGJNAME", nameOld)
            personViewModel.returnName(nameOld)
        }

        if (LastName.isEmpty()){
            personViewModel.returnLastName(lastnameOld)
        }

        if (Description.isEmpty()){
            personViewModel.returndescription(descriptionOld)
        }

        campoTextoGenerico(titulo = "Nombre", variableControl = Name, longitud = 20, personViewModel = personViewModel){
            personViewModel.returnName(it)
        }
        campoTextoGenerico(titulo = "Apellido", variableControl = LastName, longitud = 30, personViewModel = personViewModel){
            personViewModel.returnLastName(it)
        }
        campoTextoGenerico(titulo = "Descripci??n", variableControl = Description, longitud = 100, personViewModel = personViewModel){
            personViewModel.returndescription(it)
        }

        modificarDatosBoton(navigationController,userViewModel,personViewModel)
        botonModificarContrasenya(navigationController,personViewModel,userViewModel)
        botonEliminarPerfil(navigationController,personViewModel,userViewModel)
    }
}

@Composable
fun campoTextoGenerico(titulo:String, variableControl:String,longitud:Int ,mostrarTexto:Boolean =true,personViewModel: PersonViewModel, onTextChanged : (String) -> Unit) {
    val maxLength = longitud
    var myText by remember { mutableStateOf(variableControl) }
    var visualTransformation = VisualTransformation.None
    if (!mostrarTexto){
        visualTransformation = PasswordVisualTransformation()
    }
    Column() {
        TextField(
            value = myText,
            onValueChange = {
                onTextChanged(it)
                if (it.length <= maxLength) myText = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                focusedLabelColor = Color.Black
            ),

            visualTransformation = visualTransformation,
            label = { Text(text = titulo) }
        )
        Text(
            text = "${myText.length} / $maxLength",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun modificarDatosBoton(navigationController: NavHostController,userViewModel: UserViewModel,personViewModel: PersonViewModel) {

    var showToastBoton by remember { mutableStateOf(false) }

    if (showToastBoton) {
        Toast.makeText(LocalContext.current, "Datos Modificados Correctamente", Toast.LENGTH_SHORT).show()
        showToastBoton = false
    }

    Button(
        onClick = {
            personViewModel.editPerson(navigationController,userViewModel)
            showToastBoton = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Text("Modificar Datos")
    }
}



@Composable
fun TextFieldContrasenyaConIcono(textoDefecto : String, textoEjemplo : String = "", backgroundColor : Color = Color.LightGray, icono: ImageVector,personViewModel: PersonViewModel){
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Row(modifier = Modifier
        .height(65.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .padding(0.dp, 0.dp),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = password,
            onValueChange = {
                password = it
                personViewModel.returnPassword(it)
            },
            label = { Text(textoEjemplo) },
            singleLine = true,
            placeholder = { Text(textoDefecto) } ,


            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    IconoOjo()
                else IconoOjoTachado()

                IconButton(onClick = { passwordVisible = !passwordVisible }) {}
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                focusedLabelColor = Color.Black
            ),
        )
    }
}

@Composable
fun TextFieldContrasenyaRepetirConIcono(textoDefecto : String, textoEjemplo : String = "", backgroundColor : Color = Color.LightGray, icono: ImageVector,personViewModel: PersonViewModel){
    var passwordRepetir by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Row(modifier = Modifier
        .height(65.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .padding(0.dp, 0.dp),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = passwordRepetir,
            onValueChange = {
                passwordRepetir = it
                personViewModel.returnPasswordRepeat(it)
            },
            label = { Text(textoEjemplo) },
            singleLine = true,
            placeholder = { Text(textoDefecto) } ,


            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    IconoOjo()
                else IconoOjoTachado()

                IconButton(onClick = { passwordVisible = !passwordVisible }) {}
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                focusedLabelColor = Color.Black
            ),
        )
    }
}

@Composable
private fun IconoOjo()
{
    Row() {
        Icon(
            modifier = Modifier
                .width(25.dp)
                .height(40.dp)
                .padding(1.dp, 0.dp),

            painter = painterResource(id = R.drawable.view),
            contentDescription = "Mostrar Contrase??a",
        )
    }
}

@Composable
private fun IconoOjoTachado()
{
    Row() {
        Icon(
            modifier = Modifier
                .width(25.dp)
                .height(40.dp)
                .padding(1.dp, 0.dp),

            painter = painterResource(id = R.drawable.hide),
            contentDescription = "Ocultar Contrase??a",
        )
    }
}

@Composable
fun botonModificarContrasenya(navigationController: NavHostController,personViewModel: PersonViewModel,userViewModel: UserViewModel) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            showDialog.value = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text("Modificar Contrase??a")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            text = {
                Column(){
                    TextFieldContrasenyaConIcono(
                        textoDefecto = "Introducir la Contrase??a",
                        icono = Icons.Default.Lock,
                        personViewModel = personViewModel
                    )
                    TextFieldContrasenyaRepetirConIcono(
                        textoDefecto = "Repetir la Contrase??a",
                        icono = Icons.Default.Lock,
                        personViewModel = personViewModel
                    )
                }
            },
            confirmButton = {

                var showToastBoton by remember { mutableStateOf(false) }

                if (showToastBoton) {
                    Toast.makeText(LocalContext.current, "Contrase??a Modificada Correctamente", Toast.LENGTH_SHORT).show()
                    showToastBoton = false
                }

                Button(
                    onClick = { showDialog.value = false
                        personViewModel.editPassword(navigationController,userViewModel,context)
                        showToastBoton = true
                    },
                    content = { Text("Modificar contrase??a") }
                )
            },
            dismissButton = {
                Button(
                    onClick = {showDialog.value = false},
                    content = { Text("Cancelar") }
                )
            }
        )
    }
}

@Composable
fun botonEliminarPerfil(navigationController: NavHostController,personViewModel: PersonViewModel,userViewModel: UserViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            showDialog.value = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text("Borrar Perfil")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text("??Est??s seguro que deseas borrar tu perfil?")
            } },
            text = {
                Text("Esta acci??n de borrado no es reversible. ??Est??s seguro que deseas continuar?")
            },
            confirmButton = {

                var showToastBoton by remember { mutableStateOf(false) }

                if (showToastBoton) {
                    Toast.makeText(LocalContext.current, "Perfil Borrado Correctamente", Toast.LENGTH_SHORT).show()
                    showToastBoton = false
                }

                Button(
                    modifier = Modifier.padding(0.dp,0.dp,55.dp,0.dp),
                    onClick = { showDialog.value = false
                        personViewModel.deletePerson(userViewModel.email.value!!,navigationController)
                        showToastBoton = true
                    },
                    content = { Text("Aceptar") }
                )
            },
            dismissButton = {
                Button(
                    onClick = {showDialog.value = false},
                    content = { Text("Cancelar") }
                )
            }
        )
    }
}
