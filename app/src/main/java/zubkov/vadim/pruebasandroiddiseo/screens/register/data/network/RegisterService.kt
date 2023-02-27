package zubkov.vadim.pruebasandroiddiseo.screens.register.data.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity.RegisterModel
import zubkov.vadim.pruebasandroiddiseo.screens.register.data.dto.RegisterDTO
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val registerClient: RegisterClient,
    @ApplicationContext private val context: Context
){
    private val handler = Handler(Looper.getMainLooper())

    suspend fun doUser(registerUser : RegisterDTO):Boolean{
        return withContext(Dispatchers.IO){
            val response = registerClient.doRegisterJSON(registerUser)
            //response.body()?.status?.compareTo("201") == 0
            when (response.code()) {
                201 -> {
                    true
                }
                400 -> {
                    //nick
                    handler.post {
                        Toast.makeText(context, "El nickname o email ya existe", Toast.LENGTH_SHORT).show()
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