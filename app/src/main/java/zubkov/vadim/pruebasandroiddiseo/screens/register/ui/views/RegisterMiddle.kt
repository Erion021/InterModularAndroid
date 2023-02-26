package zubkov.vadim.pruebasandroiddiseo.screens.register.ui.views

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel.RegisterViewModel
import java.util.*

@Composable
fun RegisterMiddleScreen(navigationController: NavHostController, registerViewModel: RegisterViewModel) {
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
        MainMiddle(state,navigationController,registerViewModel)
    }
}

@Composable
fun MainMiddle(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painterResource(R.drawable.background),
            contentDescription = stringResource(id = R.string.icono_descripcion),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
            alpha = 0.5F
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            RegisterMiddleBody(state = state,navigationController,registerViewModel)
        }
    }
}

@Composable
fun RegisterMiddleBody(state: MutableTransitionState<Boolean>, navigationController: NavHostController, registerViewModel: RegisterViewModel){
    val nick: String by registerViewModel.nick.observeAsState(initial = "")

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
                    Nick(nick){
                        registerViewModel.onRegisterChangedNick(nick = it)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Date(registerViewModel)
                    Spacer(modifier = Modifier.padding(10.dp))
                    NextMiddleButton(navigationController)
                }
            }
        }
    }
}


@Composable
fun Nick(nick: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = nick,
        onValueChange = {
            onTextChanged(it)
        },
        placeholder = {
            Text(
                text = "Nick",
                color = Color(0xFFDAD3C8)
            )
        },
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
fun Date(registerViewModel: RegisterViewModel) {
    val context = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    var date = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
        }, mYear, mMonth, mDay
    )

    OutlinedTextField(
        value = date.value,
        onValueChange = {
            date.value = it
        },
        placeholder = {
            Text(
                text = "Fecha Nacimiento",
                color = Color(0xFFDAD3C8)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFFDAD3C8),
            unfocusedBorderColor = Color(0xFFDAD3C8)
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "",
                modifier = Modifier.clickable {
                    mDatePickerDialog.show()
                }
            )
        },
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary
        )
    )
    registerViewModel.birthDate(date.value)
}

@Composable
fun NextMiddleButton(navigationController: NavHostController) {
    Button(
        onClick = {
            navigationController.navigate(Routes.RegisterLast.route)
        },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7B7457))
    ){
        Text(
            text = "Siguiente",
            color = MaterialTheme.colors.onPrimary
        )
    }
}