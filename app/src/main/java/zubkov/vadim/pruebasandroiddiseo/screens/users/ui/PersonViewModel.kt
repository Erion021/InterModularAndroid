package zubkov.vadim.pruebasandroiddiseo.screens.users.ui

import android.util.Log
import androidx.compose.ui.graphics.vector.EmptyPath
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.UpdateFields
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

    fun returnPerson(userViewModel: UserViewModel){
        Log.d("Hola","Entro en return")
        viewModelScope.launch {
            Log.d("Hola 1","Antes del Person")
            _Person = MutableLiveData(personUseCase("${userViewModel.email.value}"))
            Log.d("Hola 2","Despues del Person")
            changeList()
            Log.d("Hola 3","Final del Person")
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
            val body = PersonModel(
                name = name.value!!,
                lastname = lastName.value!!,
                description = description.value!!,
                email = userViewModel.email.value!!,
                photo = photo,
                following = following,
                fav_routes = fav_routes,
                nick = nick,
                date = date
            )
            personUseCase.editPerson(body)
            navigationController.navigate(Routes.Home.route)
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