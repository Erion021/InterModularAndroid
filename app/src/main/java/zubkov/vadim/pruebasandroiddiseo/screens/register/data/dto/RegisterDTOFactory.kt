package zubkov.vadim.pruebasandroiddiseo.screens.register.data.dto

import zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity.RegisterModel
import javax.inject.Inject

class RegisterDTOFactory @Inject constructor(){
    operator fun invoke(
        name : String,
        lastname : String,
        date : String,
        nick : String,
        email: String,
        password: String,
        photo : String,
        description : String
    ) : RegisterDTO {
        return RegisterDTO(
            name = name,
            lastname = lastname,
            date = date,
            nick = nick,
            email = email,
            password = password,
            photo = photo,
            description = description
        )
    }
}