package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto

import javax.inject.Inject

class CommentDTOFactory @Inject constructor(){
    operator fun invoke(
        id_publication : String,
        email: String,
        date: String,
        message: String
    ) : CommentDTO {
        return CommentDTO(
            id_publication = id_publication,
            email = email,
            date = date,
            message = message
        )
    }
}