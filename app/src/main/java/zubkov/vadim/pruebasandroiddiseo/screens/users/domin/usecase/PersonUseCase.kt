package zubkov.vadim.pruebasandroiddiseo.screens.users.domin.usecase

import zubkov.vadim.pruebasandroiddiseo.screens.users.data.PersonRepository
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.UpdateFields
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.PersonModel
import javax.inject.Inject

class PersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(email: String): List<PersonDTO> {
        return repository.getPersonRepository(
            email = email
        )
    }

    suspend fun getFollowers(email: String): List<PersonDTO> {
        return repository.getFollowers(email)
    }

    suspend fun followUser(email: String, emailSeguir: String) {
        repository.followUser(email, emailSeguir)
    }

    suspend fun unfollowUser(email: String, emailSeguir: String) {
        repository.unfollowUser(email, emailSeguir)
    }

    suspend fun deletePerson(email: String){
        repository.deletePerson(email)
    }

    suspend fun editPerson(body : PersonModel){
        repository.editPerson(
            body = PersonDTO(
                name = body.name,
                lastname = body.lastname,
                email = body.email,
                photo = body.photo,
                following = body.following,
                description = body.description,
                date = body.date,
                nick = body.nick,
                fav_routes = body.fav_routes
            )
        )
    }
}