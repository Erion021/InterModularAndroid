package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.client.CommentClient
import javax.inject.Inject

class CommentService @Inject constructor(
    private val commentClient: CommentClient
) {
    suspend fun getCommentRoute(id_publication : String):List<CommentDTO>{
        return withContext(Dispatchers.IO){
            Log.d("ID PUBLICATION","$id_publication")
            val response = commentClient.getRouteComments(id_publication)
            response.body()?.data ?: emptyList()
        }
    }

    suspend fun postComment(comment : CommentDTO):Boolean{
        return withContext(Dispatchers.IO){
            val response = commentClient.postComment(comment)
            response.body()?.status?.compareTo("200") == 0
        }
    }
}