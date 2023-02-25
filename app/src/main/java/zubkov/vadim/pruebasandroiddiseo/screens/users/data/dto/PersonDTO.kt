package zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto

import com.google.gson.annotations.SerializedName
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO

data class PersonDTO(
    @SerializedName("name") val name : String,
    @SerializedName("lastname") val lastname : String,
    @SerializedName("email") val email : String,
    @SerializedName("date") val date : String,
    @SerializedName("nick") val nick : String,
    @SerializedName("following") val following : List<String>,
    @SerializedName("photo") val photo : String,
    @SerializedName("fav_routes") val fav_routes : List<String>
)