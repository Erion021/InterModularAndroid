package zubkov.vadim.pruebasandroiddiseo.screens.users.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.vector.EmptyPath
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.mindrot.jbcrypt.BCrypt
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.*
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.usecase.PersonUseCase
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personUseCase: PersonUseCase,
    private val personModelFactory: PersonModelFactory
) : ViewModel() {
    private var _Person = MutableLiveData<List<PersonDTO>>()
    var person : LiveData<List<PersonDTO>> = _Person

    private var _name = MutableLiveData<String>()
    var name : LiveData<String> = _name

    private var _lastName = MutableLiveData<String>()
    var lastName : LiveData<String> = _lastName

    private var _description = MutableLiveData<String>()
    var description : LiveData<String> = _description

    private var _password = MutableLiveData<String>()
    var password : LiveData<String> = _password

    private var _passwordRepeat = MutableLiveData<String>()
    var passwordRepeat : LiveData<String> = _passwordRepeat

    fun changeList(){
        person = _Person
    }

    fun returnName(name : String){
        _name.value = name
    }

    fun returnLastName(lastname : String){
        _lastName.value = lastname
    }

    fun returndescription(description : String){
        _description.value = description
    }

    fun returnPassword(password : String){
        _password.value = password
    }

    fun returnPasswordRepeat(passwordRepeat : String){
        _passwordRepeat.value = passwordRepeat
    }

    fun returnPerson(userViewModel: UserViewModel){
        viewModelScope.launch {
            _Person = MutableLiveData(personUseCase("${userViewModel.email.value}"))
            changeList()
        }
    }

    fun returnPerson(email:String):PersonDTO?{
        var person: PersonDTO?
        runBlocking {
            person = personUseCase(email).first()
        }
        return person
    }

    fun editPerson(navigationController: NavHostController,userViewModel: UserViewModel){
        viewModelScope.launch {
            val body = personModelFactory(
                name = name.value!!,
                lastname = lastName.value!!,
                description = description.value!!,
                email = userViewModel.email.value!!,
                photo = photo,
                following = following,
                fav_routes = fav_routes,
                nick = nick,
                date = date,
                password = passwordOld
            )

            personUseCase.editPerson(body)
            navigationController.navigate(Routes.Home.route)
        }
    }

    fun editPassword(navigationController: NavHostController,userViewModel: UserViewModel,context : Context){
        viewModelScope.launch {
            if (password.value!!.compareTo(passwordRepeat.value!!) == 0){
                val hashedPassword = BCrypt.hashpw(password.value!!, BCrypt.gensalt(10))
                val body = personModelFactory(
                    name = nameOld,
                    lastname = lastnameOld,
                    description = descriptionOld,
                    email = userViewModel.email.value!!,
                    photo = photo,
                    following = following,
                    fav_routes = fav_routes,
                    nick = nick,
                    date = date,
                    password = hashedPassword
                )
                personUseCase.editPassword(body)
                navigationController.navigate(Routes.Home.route)
            } else {
                Toast.makeText(context,"Las contraseñeas deben coincidir", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deletePerson(email : String,navigationController: NavHostController){
        viewModelScope.launch {
            personUseCase.deletePerson(email)
            navigationController.navigate(Routes.Login.route)
        }
    }

    fun followUser(email:String, emailSeguir: String){
        viewModelScope.launch {
            personUseCase.followUser(email,emailSeguir)
        }
    }

    fun unfollowUser(email:String, emailSeguir: String){
        viewModelScope.launch {
            personUseCase.unfollowUser(email,emailSeguir)
        }
    }

    fun followers(email:String):List<String>{
        var followers : List<String>
        runBlocking {
            followers = personUseCase.getFollowers(email).map{it.email}
        }
        return followers
    }
}