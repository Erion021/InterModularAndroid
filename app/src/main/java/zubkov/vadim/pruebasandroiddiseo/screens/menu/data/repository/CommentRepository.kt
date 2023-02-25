package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.repository

import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.service.CommentService
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val api : CommentService
) {
    suspend fun getRoutesComments(id_publication : String):List<CommentDTO>{
        return api.getCommentRoute(id_publication)
    }

    suspend fun postComment(comment : CommentDTO) : Boolean{
        return api.postComment(comment)
    }
}