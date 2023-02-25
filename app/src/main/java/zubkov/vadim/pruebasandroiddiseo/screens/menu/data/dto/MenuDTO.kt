package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class MenuDTO(
    @SerializedName("_id") var _id: String = "",
    @SerializedName("email") var email: String,
    @SerializedName("date") var date: String,
    @SerializedName("name") var name:String,
    @SerializedName("category") var category:String,
    @SerializedName("distance") var distance:Int,
    @SerializedName("difficulty") var difficulty:String,
    @SerializedName("duration") var duration:Long,
    @SerializedName("description") var description: String,
    @SerializedName("privacy") var privacy: String,
    @SerializedName("photo") var photo: List<String>,
    @SerializedName("lat_A") var lat_A: Double,
    @SerializedName("lng_A") var lng_A: Double,
    @SerializedName("lat_B") var lat_B: Double,
    @SerializedName("lng_B") var lng_B: Double,
    @SerializedName("rec_movement") var rec_movement: List<LatLng>
)