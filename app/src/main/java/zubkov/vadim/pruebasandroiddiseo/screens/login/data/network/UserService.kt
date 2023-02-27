package zubkov.vadim.pruebasandroiddiseo.screens.login.data.network

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.login.data.dto.UserDTO
import zubkov.vadim.pruebasandroiddiseo.dependecyinjection.Token
import javax.inject.Inject

class UserService @Inject constructor(
    private val userClient: UserClient,
    @ApplicationContext private val context: Context
) {
    private val handler = Handler(Looper.getMainLooper())

    suspend fun doUser(user: UserDTO): Boolean {
        return withContext(Dispatchers.IO) {
            val response = userClient.doUser(user)
            Token = response.body()?.accessToken.toString()
            //response.body()?.status?.compareTo("201") == 0
            when (response.code()) {
                201 -> {
                    true
                }
                400 -> {
                    //Username or passsword incorrect
                    handler.post {
                        Toast.makeText(context, "Contraseña o usuario incorrecto", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
                403 -> {
                    //Attemps account loggin
                    handler.post {
                        Toast.makeText(context, "Demasiados Intentos,vuelva mas tarde", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
                else -> {
                    handler.post {
                        Toast.makeText(context, "Rare error", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
            }
        }
    }
}
