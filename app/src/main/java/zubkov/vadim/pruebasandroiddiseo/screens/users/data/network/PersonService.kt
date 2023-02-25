package zubkov.vadim.pruebasandroiddiseo.screens.users.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request.FollowBody
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request.UnfollowBody
import javax.inject.Inject

class PersonService @Inject constructor(
    private val personClient: PersonClient
){
    suspend fun getPerson(
        email: String
    ):List<PersonDTO>{
        return withContext(Dispatchers.IO){
            Log.d("Email Service","$email")
            val response = personClient.getPersonByEmail(email)
            response.body()?.data ?: emptyList()
        }
    }

    suspend fun getFollowers(
        email: String
    ):List<PersonDTO>{
        return withContext(Dispatchers.IO){
            val response = personClient.getFollowers(email)
            response.body()?.data ?: emptyList()
        }
    }

    suspend fun followUser(email:String,emailSeguir:String){
        personClient.followUser(FollowBody(email,emailSeguir))
    }

    suspend fun unfollowUser(email:String,emailSeguir:String){
        personClient.unfollowUser(UnfollowBody(email,emailSeguir))
    }

    suspend fun editPerson(body : PersonDTO){
        personClient.editPerson(body)
    }

    suspend fun deletePerson(email : String){
        personClient.deletePerson(email)
    }
}