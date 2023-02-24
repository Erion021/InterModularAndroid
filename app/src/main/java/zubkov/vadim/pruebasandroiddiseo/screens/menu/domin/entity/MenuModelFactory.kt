package zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MenuModelFactory @Inject constructor(){
    operator fun invoke(
        id:String,
        email: String,
        date: String,
        name:String,
        category:String,
        distance:Double,
        difficulty:String,
        duration:Long,
        description: String,
        privacy: String,
        photo: List<String>,
        lat_A: Double,
        lng_A: Double,
        lat_B: Double,
        lng_B: Double,
        rec_movement: List<LatLng>
    ) : MenuModel {
        return MenuModel(
            id = id,
            email = email,
            date = date,
            name = name,
            category = category,
            distance = distance,
            difficulty = difficulty,
            duration = duration,
            description = description,
            privacy = privacy,
            photo = photo,
            lat_A = lat_A,
            lng_A = lng_A,
            lat_B = lat_B,
            lng_B = lng_B,
            rec_movement = rec_movement
        )
    }
}