package zubkov.vadim.pruebasandroiddiseo.screens.users.data.network.request

import com.google.gson.annotations.SerializedName

class UnfollowBody (
    @SerializedName("email") var email: String,
    @SerializedName("emailToUnfollow") var emailToUnfollow: String
)