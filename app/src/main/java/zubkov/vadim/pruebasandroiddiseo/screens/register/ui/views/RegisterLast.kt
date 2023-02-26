package zubkov.vadim.pruebasandroiddiseo.screens.register.ui.views

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel.RegisterViewModel

var comprobacionRapida = false

@Composable
fun RegisterLastScreen(navigationController: NavHostController, registerViewModel: RegisterViewModel) {
    var state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MainLastScreen(state,navigationController,registerViewModel)
    }
}

@Composable
fun MainLastScreen(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painterResource(R.drawable.background),
            contentDescription = stringResource(id = R.string.icono_descripcion),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            alpha = 0.5F
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            RegisterLastBody(state = state,navigationController,registerViewModel)
        }
    }
}

@Composable
fun RegisterLastBody(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    val email: String by registerViewModel.email.observeAsState(initial = "")
    val password: String by registerViewModel.password.observeAsState(initial = "")
    val passwordRepeat: String by registerViewModel.repeatPassword.observeAsState(initial = "")

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(
            animationSpec = tween(
                delayMillis = 400,
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.width(275.dp)
            ){
                Column {
                    Email(email){
                        registerViewModel.onRegisterChangedPassword_Email(email = it,password = password, passwordRepeat = passwordRepeat)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Password(password){
                        registerViewModel.onRegisterChangedPassword_Email(email = email,password = it, passwordRepeat = passwordRepeat)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    RepeatPassword(passwordRepeat){
                        registerViewModel.onRegisterChangedPassword_Email(email = email,password = password, passwordRepeat = it)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    TermsAndConditions(navigationController = navigationController)
                    Spacer(modifier = Modifier.padding(10.dp))
                    ButtonRegister(navigationController = navigationController, registerViewModel = registerViewModel)
                }
            }
        }
    }
}

@Composable
fun Email(email:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = email,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Email",
            color = Color(0xFFDAD3C8)
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDAD3C8),
            unfocusedBorderColor = Color(0xFFDAD3C8)
        ),
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary
        )
    )
}

@Composable
fun Password(password:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = password,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Contraseña",
            color = Color(0xFFDAD3C8)
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDAD3C8),
            unfocusedBorderColor = Color(0xFFDAD3C8)
        ),
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary
        )
    )
}

@Composable
fun RepeatPassword(repeatPassword:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = repeatPassword,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Repetir Contraseña",
            color = Color(0xFFDAD3C8)
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDAD3C8),
            unfocusedBorderColor = Color(0xFFDAD3C8)
        ),
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary
        )
    )
}

@Composable
fun TermsAndConditions(navigationController: NavHostController) {
    var confirmar by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = confirmar,
            onCheckedChange = {
                confirmar = !confirmar
                comprobacionRapida = confirmar
            }
        )
        androidx.compose.foundation.text.ClickableText(
            text = AnnotatedString("Aceptar los terminos de servicios y condiciones para el programa"),
            onClick = {

            },
            style = TextStyle(
                color = Color(0xFFDAD3C8)
            )
        )
    }
}

@Composable
fun ButtonRegister(navigationController: NavHostController,registerViewModel: RegisterViewModel) {
    val context : Context = LocalContext.current

    Button(
        onClick = {
            if (comprobacionRapida){
                registerViewModel.onButtonRegisterPress(navigationController,context)
            } else {
                Toast.makeText(context,"No acepto los terminos y condiciones", Toast.LENGTH_SHORT).show()
            }
        },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B7457))
    ){
        Text(
            text = "Registrarse",
            color = MaterialTheme.colors.onPrimary
        )
    }
}

