package zubkov.vadim.pruebasandroiddiseo.screens.login

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel

@Composable
fun Login(navigationController: NavHostController, userViewModel:UserViewModel) {
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
        MainScreen(state,navigationController,userViewModel)
    }
}

@Composable
fun MainScreen(state: MutableTransitionState<Boolean>, navigationController: NavHostController, userViewModel: UserViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painterResource(R.drawable.background),
            contentDescription = stringResource(id = R.string.icono_descripcion),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            alpha = 0.5F
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            MainLogin(state = state,navigationController,userViewModel)
        }
    }
}

@Composable
fun MainLogin(state: MutableTransitionState<Boolean>, navigationController: NavHostController,userViewModel: UserViewModel){
    val email: String by userViewModel.email.observeAsState(initial = "")
    val password: String by userViewModel.password.observeAsState(initial = "")

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(
            animationSpec = tween(
                delayMillis = 400,
                durationMillis = 2700,
                easing = LinearOutSlowInEasing
            )
        ) + slideInVertically(
            animationSpec = tween(
                delayMillis = 400,
                durationMillis = 2700,
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
            Image()
            Spacer(modifier = Modifier.padding(15.dp))
            Box(
                modifier = Modifier.width(275.dp)
            ){
                Column {
                    Email(email){
                        userViewModel.onLoginChanged(email = it ,password = password)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Password(password){
                            userViewModel.onLoginChanged(email = email ,password = it)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    ButtonLogin(navigationController,userViewModel)
                }
            }
            Spacer(modifier = Modifier.padding(6.dp))
            ClickableText(navigationController)
        }
    }
}

@Composable
fun Image(){
    Box(
        Modifier
            .height(250.dp)
            .width(250.dp)
    ){
        Image(
            painterResource(R.drawable.fotoi_inicio),
            contentDescription = stringResource(id = R.string.icono_descripcion),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
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
            color = MaterialTheme.colors.onPrimary
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onPrimary,
            unfocusedBorderColor = MaterialTheme.colors.onPrimary
        ),
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary
        )
    )
}

@Composable
fun Password(contrasenya:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = contrasenya,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Contraseña",
            color = MaterialTheme.colors.onPrimary
        ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onPrimary,
            unfocusedBorderColor = MaterialTheme.colors.onPrimary
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
fun ButtonLogin(navigationController: NavHostController,userViewModel: UserViewModel) {
    val mContext = LocalContext.current

    Button(
        onClick = {
            userViewModel.onButtonLoginPress(navigationController,mContext)
        },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSecondary)
    ){
        Text(
            text = "Login",
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun ClickableText(navigationController: NavHostController){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        ClickableText(
            text = AnnotatedString("Registrarse"),
            onClick = {
                navigationController.navigate(Routes.Register.route)
            },
            style = TextStyle(
                color = MaterialTheme.colors.onPrimary
            )
        )
    }
}