package zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity

import com.google.android.gms.maps.model.LatLng

data class MenuModel(
    var _id : String = "",
    var email: String,
    var date: String,
    var name:String,
    var category:String,
    var distance:Int,
    var difficulty:String,
    var duration:Long,
    var description: String,
    var privacy: String,
    var photo: List<String>,
    var lat_A: Double,
    var lng_A: Double,
    var lat_B: Double,
    var lng_B: Double,
    var rec_movement: List<LatLng>
)