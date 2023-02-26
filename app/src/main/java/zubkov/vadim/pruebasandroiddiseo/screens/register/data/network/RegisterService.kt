package zubkov.vadim.pruebasandroiddiseo.screens.register.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity.RegisterModel
import zubkov.vadim.pruebasandroiddiseo.screens.register.data.dto.RegisterDTO
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val registerClient: RegisterClient
){
    suspend fun doUser(registerUser : RegisterDTO):Boolean{
        return withContext(Dispatchers.IO){
            val response = registerClient.doRegisterJSON(registerUser)
            response.body()?.status?.compareTo("201") == 0
        }
    }
}