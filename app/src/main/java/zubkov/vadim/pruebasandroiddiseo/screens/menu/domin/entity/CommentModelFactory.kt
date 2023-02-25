package zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity

import javax.inject.Inject

class CommentModelFactory @Inject constructor(){
    operator fun invoke(
        id_publication : String,
        email: String,
        date: String,
        message: String
    ) : CommentModel {
        return CommentModel(
            id_publication = id_publication,
            email = email,
            date = date,
            message = message
        )
    }
}