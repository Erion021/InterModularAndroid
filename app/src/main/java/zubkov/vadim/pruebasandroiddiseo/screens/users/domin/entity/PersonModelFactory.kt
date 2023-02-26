package zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity

import javax.inject.Inject

class PersonModelFactory @Inject constructor(){
    operator fun invoke(
        name : String,
        lastname : String,
        email : String,
        date : String,
        nick : String,
        following : List<String>,
        photo : String,
        description : String,
        fav_routes : List<String>,
        password : String
    ) : PersonModel {
        return PersonModel(
            name = name,
            lastname = lastname,
            email = email,
            date = date,
            nick = nick,
            following = following,
            photo = photo,
            description = description,
            fav_routes = fav_routes,
            password = password
        )
    }
}