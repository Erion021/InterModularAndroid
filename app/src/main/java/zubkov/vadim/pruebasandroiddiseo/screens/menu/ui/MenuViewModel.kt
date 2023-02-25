package zubkov.vadim.pruebasandroiddiseo.screens.menu.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity.CommentModelFactory
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.usecase.CommentUseCase
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.usecase.MenuUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuUseCase: MenuUseCase,
    private val commentUseCase: CommentUseCase,
    private val commentModelFactory: CommentModelFactory
) : ViewModel() {
    private var _RoutesList = MutableLiveData<List<MenuDTO>>()
    var routesList : LiveData<List<MenuDTO>> = _RoutesList

    fun changeList(){
        routesList = _RoutesList
    }

    fun changeCommentList(){
        commentList = _CommentList
    }

    private var _CommentList = MutableLiveData<List<CommentDTO>>()
    var commentList : LiveData<List<CommentDTO>> = _CommentList

    private var _emailComment = MutableLiveData<String>()
    var emailComment : LiveData<String> = _emailComment

    private var _message = MutableLiveData<String>()
    var message : LiveData<String> = _message

    private var _dateComment = MutableLiveData<String>()
    var dateComment : LiveData<String> = _dateComment

    private var _idPublication = MutableLiveData<String>()
    var idPublication : LiveData<String> = _idPublication

    private var _RoutesListByEmail = MutableLiveData<List<MenuDTO>>()
    var routesListByEmail : LiveData<List<MenuDTO>> = _RoutesListByEmail

    private fun getTime(): String? {
        val myDateObj = LocalDateTime.now()
        val myFormatObj: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        return myDateObj.format(myFormatObj)
    }

    fun onMessageChange(message: String){
        _message.value = message
    }

    fun changeListByEmail(){
        routesListByEmail = _RoutesListByEmail
    }

    fun devolverListaByEmail(email : String){
        viewModelScope.launch {
            Log.d("EMAIL DEV ROUTES",email)
            _RoutesListByEmail = MutableLiveData(menuUseCase.getRouteByEmail(email))
            changeListByEmail()
        }
    }

    fun postComment(userViewModel: UserViewModel, idPublication : String){
        viewModelScope.launch {
            Log.d("id",idPublication)
            Log.d("email",userViewModel.email.value!!)
            Log.d("message",message.value!!)
            Log.d("date","date")
            val comment = commentModelFactory(
                id_publication = idPublication,
                email = userViewModel.email.value!!,
                message = message.value!!,
                date = "adat"
            )
            val result = commentUseCase.postComment(comment)
            if (result){
                Log.d("FUNCIONO","SIIII")
            }
        }
    }

    fun devolverLista(){
        viewModelScope.launch {
            _RoutesList = MutableLiveData(menuUseCase())
            changeList()
        }
    }

    fun devolverComentariosRuta(id_publication : String){
        viewModelScope.launch {
            _CommentList = MutableLiveData(commentUseCase.invoke(id_publication))
            changeCommentList()
        }
    }

    private var _actualRoute = MutableLiveData<MenuDTO>()
    var actualRoute : LiveData<MenuDTO> = _actualRoute

    fun updateActualRoute(route:MenuDTO){
        _actualRoute.value = route
    }

    fun likeRuta(email:String, idRuta: String){
        viewModelScope.launch {
            menuUseCase.likeRoute(email,idRuta)
        }
    }

    fun unlikeRuta(email:String, idRuta: String){
        viewModelScope.launch {
            menuUseCase.unlikeRoute(email,idRuta)
        }
    }
}