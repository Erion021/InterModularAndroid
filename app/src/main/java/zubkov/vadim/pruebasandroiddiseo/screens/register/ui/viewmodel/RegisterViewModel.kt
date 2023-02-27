package zubkov.vadim.pruebasandroiddiseo.screens.register.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity.RegisterModelFactory
import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.usecase.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val registerModelFactory: RegisterModelFactory,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _dateReg = MutableLiveData<String>()
    val dateReg: LiveData<String> = _dateReg

    private val _nick = MutableLiveData<String>()
    val nick: LiveData<String> = _nick

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    private val _isButtonLoginEnable = MutableLiveData<Boolean>()
    val isButtonLoginEnable: LiveData<Boolean> = _isButtonLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun birthDate(dateReg: String){
        _dateReg.value = dateReg
    }

    fun onRegisterChangedName_LastName(name: String, lastName: String) {
        _name.value = name
        _lastName.value = lastName
    }

    fun onRegisterChangedNick(nick: String) {
        _nick.value = nick
    }

    fun onRegisterChangedPassword_Email(email:String,password: String, passwordRepeat: String){
        _email.value = email
        _password.value = password
        _repeatPassword.value = passwordRepeat
    }

    fun onButtonRegisterPress(navigationController: NavHostController,context : Context) {
        if (name.value == null || email.value == null){
            Toast.makeText(context,"Debe introducir los datos", Toast.LENGTH_SHORT).show()
        } else if (password.value!!.length < 8 && repeatPassword.value!!.length < 8) {
            Toast.makeText(context,"Las contraseña es demasiado corta", Toast.LENGTH_SHORT).show()
        } else if (repeatPassword.value?.let { password.value?.compareTo(it) ?: 0 } != 0) {
            Toast.makeText(context,"Las contraseñeas deben coincidir", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("VALUES REGISTER", name.value!! +
                    lastName.value!! +
                    dateReg.value!! +
                    email.value!! +
                    password.value!!
            )
            viewModelScope.launch {
                Log.i("Mensaje", "Hola?")
                val userRegister = registerModelFactory(
                    name = name.value!!,
                    lastname = lastName.value!!,
                    date = dateReg.value!!,
                    nick = nick.value!!,
                    email = email.value!!,
                    password = password.value!!,
                    description = "Agregue una descripcion",
                    photo = ""
                )
                val result = registerUseCase(userRegister)
                if(result) {
                    Log.i("Mensaje", "Registro Completo")
                    navigationController.navigate(route = Routes.Login.route)
                }
            }
        }
    }
}