package zubkov.vadim.pruebasandroiddiseo.screens.register

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navigationController: NavHostController, registerViewModel: RegisterViewModel) {
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
        Main(state,navigationController,registerViewModel)
    }
}

@Composable
fun Main(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painterResource(R.drawable.background),
            contentDescription = stringResource(id = R.string.icono_descripcion),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            alpha = 0.5F
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            RegisterBody(state = state,navigationController,registerViewModel)
        }
    }
}

@Composable
fun RegisterBody(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    val name: String by registerViewModel.name.observeAsState(initial = "")
    val lastName: String by registerViewModel.lastName.observeAsState(initial = "")

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
                    Name(name){
                        registerViewModel.onRegisterChangedName_LastName(name = it,lastName = lastName)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    LastName(lastName){
                        registerViewModel.onRegisterChangedName_LastName(name = name,lastName = it)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    NextButton(navigationController)
                }
            }
        }
    }
}

@Composable
fun Name(name:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = name,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Nombre",
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
fun LastName(lastName:String, onTextChanged: (String) -> Unit){
    OutlinedTextField(
        value = lastName,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = { Text(
            text = "Apellido",
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
fun NextButton(navigationController: NavHostController) {
    Button(
        onClick = {
            navigationController.navigate(Routes.RegisterMiddle.route)
        },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B7457))
    ){
        Text(
            text ="Siguiente",
            color = MaterialTheme.colors.onPrimary
        )
    }
}
