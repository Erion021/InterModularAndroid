package zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity

class PersonModelFactory {
    operator fun invoke(
        name : String,
        lastname : String,
        email : String,
        date : String,
        nick : String,
        following : List<String>,
        photo : String
    ) : PersonModel {
        return PersonModel(
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