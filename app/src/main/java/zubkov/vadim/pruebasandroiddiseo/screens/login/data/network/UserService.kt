package zubkov.vadim.pruebasandroiddiseo.screens.login.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.login.data.dto.UserDTO
import zubkov.vadim.pruebasandroiddiseo.dependecyinjection.Token
import javax.inject.Inject

class UserService @Inject constructor(
    private val userClient: UserClient
){
    suspend fun doUser(user : UserDTO):Boolean{
        return withContext(Dispatchers.IO){
            val response = userClient.doUser(user)
            Token = response.body()?.accessToken.toString()
            response.body()?.status?.compareTo("201") == 0
        }
    }
}