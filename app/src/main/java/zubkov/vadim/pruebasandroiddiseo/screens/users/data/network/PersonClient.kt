package zubkov.vadim.pruebasandroiddiseo.screens.users.data.network

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Response
import retrofit2.http.*
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request.FollowBody
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request.UnfollowBody
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.response.PersonResponse

interface PersonClient {
    @GET("/users/{email}")
    suspend fun getPersonByEmail(
        @Path("email") email: String,
    ): Response<PersonResponse>

    @GET("/users/followers/{email}")
    suspend fun getFollowers(
        @Path("email")  email: String,
    ): Response<PersonResponse>

    @POST("/users/follow")
    suspend fun followUser(
        @Body body: FollowBody
    )

    @POST("/users/unfollow")
    suspend fun unfollowUser(
        @Body body: UnfollowBody
    )

    @DELETE("/users/{email}")
    suspend fun deletePerson(
        @Path("email") email : String
    )

    @PUT("/users")
    suspend fun editPerson(
        @Body body : PersonDTO
    )
}