package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response

import com.google.gson.annotations.SerializedName
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.CommentDTO

data class CommentResponse (
    @SerializedName("data") val data: List<CommentDTO>,
    @SerializedName("status") val status: String
)

data class CommentResponsePost (
    @SerializedName("data") val data: Any,
    @SerializedName("status") val status: String
)