package zubkov.vadim.pruebasandroiddiseo.screens.login.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.login.domin.entity.UserModelFactory
import zubkov.vadim.pruebasandroiddiseo.screens.login.domin.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val loginUseCase: UserUseCase,
    private val userModelFactory: UserModelFactory
) : ViewModel(){

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun onButtonLoginPress(navigationController: NavHostController,context: Context) {
        if (email.value == null && password.value == null){
            Toast.makeText(context,"Debe introducir los datos", Toast.LENGTH_SHORT).show()
        } else {
            viewModelScope.launch {
                val usuario = userModelFactory(email.value!!,password.value!!)
                val result = loginUseCase(usuario)
                if(result) {
                    Log.i("Mensaje", "Inicio Sesion")
                    navigationController.navigate(route = Routes.Home.route)
                }
            }
        }
    }
}