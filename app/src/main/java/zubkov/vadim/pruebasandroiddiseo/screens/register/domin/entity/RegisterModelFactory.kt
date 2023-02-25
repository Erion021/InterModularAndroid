package zubkov.vadim.pruebasandroiddiseo.screens.register.domin.entity

import javax.inject.Inject

class RegisterModelFactory @Inject constructor(){
    operator fun invoke(
        name : String,
        lastname : String,
        date : String,
        nick : String,
        email: String,
        password: String,
        description : String,
        photo : String
    ) : RegisterModel {
        return RegisterModel(
            name = name,
            lastname = lastname,
            date = date,
            nick = nick,
            email = email,
            password = password,
            description = description,
            photo = photo
        )
    }
}