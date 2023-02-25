package zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.usecase

import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.repository.CommentRepository
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity.CommentModel
import javax.inject.Inject

class CommentUseCase @Inject constructor(
    private val repository : CommentRepository
) {
    suspend operator fun invoke(id_publication : String) : List<CommentDTO>{
        return repository.getRoutesComments(id_publication)
    }

    suspend fun postComment(comment : CommentModel) :Boolean {
        return repository.postComment(
            comment = CommentDTO(
                id_publication = comment.id_publication,
                email = comment.email,
                message = comment.message,
                date = comment.date
            )
        )
    }
}