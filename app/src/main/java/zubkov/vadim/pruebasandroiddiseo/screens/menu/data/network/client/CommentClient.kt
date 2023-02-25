package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response.CommentResponse
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response.CommentResponsePost

interface CommentClient {
    @GET("/comments/publication/{id_publication}")
    suspend fun getRouteComments(@Path("id_publication") id_publication : String): Response<CommentResponse>

    @POST("/comments")
    suspend fun postComment(@Body comment : CommentDTO) : Response<CommentResponsePost>
}