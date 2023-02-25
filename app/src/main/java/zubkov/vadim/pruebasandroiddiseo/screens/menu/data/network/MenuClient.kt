package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response.MenuResponse

interface MenuClient {
    @GET("/publications")
    suspend fun getRoutesList(): Response<MenuResponse>

    @POST("/publications/like")
    suspend fun likeRoute(
        @Body body:LikeBody
    )

    @POST("/publications/unlike")
    suspend fun unlikeRoute(
        @Body body:LikeBody
    )
}