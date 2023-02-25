package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.LikeBody
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response.MenuResponse

interface MenuClient {
    @GET("/publications")
    suspend fun getRoutesList(): Response<MenuResponse>

    @POST("/publications/like")
    suspend fun likeRoute(
        @Body body: LikeBody
    )

    @POST("/publications/unlike")
    suspend fun unlikeRoute(
        @Body body: LikeBody
    )

    @GET("/publications/user/{email}")
    suspend fun getRouteByEmail(@Path("email") email : String): Response<MenuResponse>
}