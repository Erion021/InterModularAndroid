package zubkov.vadim.pruebasandroiddiseo.screens.register.domin.usecase

import zubkov.vadim.pruebasandroiddiseo.screens.register.data.RegisterRepository
import zubkov.vadim.pruebasandroiddiseo.screens.register.data.dto.RegisterDTO
import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity.RegisterModel
import zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity.photo
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
){
    suspend operator fun invoke(
        registerUser : RegisterModel
    ) : Boolean {
        return repository.doRegister(
            registerUser = RegisterDTO(
                name = registerUser.name,
                lastname = registerUser.lastname,
                date = registerUser.date,
                nick = registerUser.nick,
                email = registerUser.email,
                password = registerUser.password,
                photo = registerUser.photo,
                description = registerUser.description
            )
        )
    }
}