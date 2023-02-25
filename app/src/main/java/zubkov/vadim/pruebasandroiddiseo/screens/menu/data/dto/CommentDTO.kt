package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto

import com.google.gson.annotations.SerializedName

data class CommentDTO (
    @SerializedName("id_publication") var id_publication : String,
    @SerializedName("email") var email: String,
    @SerializedName("date") var date: String,
    @SerializedName("message") var message: String,
)