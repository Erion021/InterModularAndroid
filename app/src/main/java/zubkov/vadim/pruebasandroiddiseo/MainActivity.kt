package zubkov.vadim.pruebasandroiddiseo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.ui.MenuViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.*
import zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel.RegisterViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel
import zubkov.vadim.pruebasandroiddiseo.ui.theme.PruebasAndroidDiseñoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel : UserViewModel by viewModels()
    private val mapViewModel : MapViewModel by viewModels()
    private val registerViewModel : RegisterViewModel by viewModels()
    private val menuViewModel : MenuViewModel by viewModels()
    private val personViewModel : PersonViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebasAndroidDiseñoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.onSurface
                ) {
                    CustomNavigator(userViewModel,mapViewModel,registerViewModel,menuViewModel,personViewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PruebasAndroidDiseñoTheme {
        //CustomNavigator(userViewModel)
    }
}