package zubkov.vadim.pruebasandroiddiseo.screens.users.data

import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.PersonService
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.PersonModel
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val api : PersonService
) {
    suspend fun getPersonRepository(email: String):List<PersonDTO>{
        return api.getPerson(email)
    }

    suspend fun getFollowers(email: String):List<PersonDTO>{
        return api.getFollowers(email)
    }

    suspend fun followUser(email: String,emailSeguir: String){
        return api.followUser(email,emailSeguir)
    }

    suspend fun unfollowUser(email: String,emailSeguir: String){
        return api.unfollowUser(email,emailSeguir)
    }

    suspend fun editPerson(body : PersonDTO){
        return api.editPerson(body)
    }

    suspend fun deletePerson(email : String){
        return api.deletePerson(email)
    }
}