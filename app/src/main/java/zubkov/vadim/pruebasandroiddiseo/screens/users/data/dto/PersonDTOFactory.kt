package zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto

import javax.inject.Inject

class PersonDTOFactory @Inject constructor(){
    operator fun invoke(
        name : String,
        lastname : String,
        email : String,
        date : String,
        nick : String,
        following : List<String>,
        photo : String
    ) : PersonDTO {
        return PersonDTO(
            name = name,
            lastname = lastname,
            email = email,
            date = date,
            nick = nick,
            following = following,
            photo = photo
        )
    }
}