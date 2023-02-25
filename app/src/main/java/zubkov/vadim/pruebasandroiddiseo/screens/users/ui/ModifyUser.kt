package zubkov.vadim.pruebasandroiddiseo.screens.users.ui

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

var Descripcion = ""
var Apellido = ""
var Nombre = ""

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

        campoTextoGenerico(titulo = "Nombre", variableControl = Nombre, longitud = 20)
        campoTextoGenerico(titulo = "Apellido", variableControl = Apellido, longitud = 30)
        campoTextoGenerico(titulo = "Descripción", variableControl = Descripcion, longitud = 100)

        modificarDatosBoton()
        botonModificarContrasenya()
        botonEliminarPerfil(navigationController,personViewModel,userViewModel)
    }
}

@Composable
fun campoTextoGenerico(titulo:String, variableControl:String,longitud:Int ,mostrarTexto:Boolean =true
) {
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
fun modificarDatosBoton() {

    var showToastBoton by remember { mutableStateOf(false) }

    if (showToastBoton) {
        Toast.makeText(LocalContext.current, "Datos Modificados Correctamente", Toast.LENGTH_SHORT).show()
        showToastBoton = false
    }

    Button(
        onClick = {
            //accion de modificar la descripción, nombre y apellido del usuario en la API
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
fun TextFieldContrasenyaConIcono(textoDefecto : String, textoEjemplo : String = "", backgroundColor : Color = Color.LightGray, icono: ImageVector){
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
            onValueChange = { password = it },
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
            contentDescription = "Mostrar Contraseña",
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
            contentDescription = "Ocultar Contraseña",
        )
    }
}

@Composable
fun botonModificarContrasenya() {

    val showDialog = remember { mutableStateOf(false) }
    Button(
        onClick = {
            showDialog.value = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text("Modificar Contraseña")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            text = {
                Column(){
                    TextFieldContrasenyaConIcono(
                        textoDefecto = "Introducir la Contraseña",
                        icono = Icons.Default.Lock
                    )
                    TextFieldContrasenyaConIcono(
                        textoDefecto = "Repetir la Contraseña",
                        icono = Icons.Default.Lock
                    )
                }
            },
            confirmButton = {

                var showToastBoton by remember { mutableStateOf(false) }

                if (showToastBoton) {
                    Toast.makeText(LocalContext.current, "Contraseña Modificada Correctamente", Toast.LENGTH_SHORT).show()
                    showToastBoton = false
                }

                Button(
                    onClick = { showDialog.value = false
                        //accion de modificar contraseña del usuario en la API
                        showToastBoton = true
                    },
                    content = { Text("Modificar contraseña") }
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
            personViewModel.deletePerson(userViewModel.email.value!!,navigationController)
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
                Text("¿Estás seguro que deseas borrar tu perfil?")
            } },
            text = {
                Text("Esta acción de borrado no es reversible. ¿Estás seguro que deseas continuar?")
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
                        //accion de borrar el usuario en la API
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
