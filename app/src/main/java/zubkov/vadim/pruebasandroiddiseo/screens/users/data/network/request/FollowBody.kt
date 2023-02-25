package zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request

import com.google.gson.annotations.SerializedName

class FollowBody (
    @SerializedName("email") var email: String,
    @SerializedName("emailToFollow") var emailToFollow: String
)