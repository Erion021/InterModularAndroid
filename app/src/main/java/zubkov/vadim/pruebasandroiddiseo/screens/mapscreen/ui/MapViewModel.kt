package zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.domin.usecase.MapUseCase
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity.MenuModelFactory
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase,
    private val mapModelFactory : MenuModelFactory
): ViewModel() {
    val uiSettings = MapUiSettings(
        myLocationButtonEnabled = true,
        compassEnabled = true,
        zoomControlsEnabled = false
    )

    val properties = MapProperties(
        isMyLocationEnabled = true,
        mapType = MapType.TERRAIN
    )

    private var _routeName = MutableLiveData<String>()
    var routeName : MutableLiveData<String> = _routeName

    fun getRouteName(routeName: String){
        _routeName.value = routeName
    }

    private var _description = MutableLiveData<String>()
    var description : MutableLiveData<String> = _description

    fun getDescription(description: String){
        _description.value = description
    }

    private var _category = MutableLiveData<String>()
    var category : MutableLiveData<String> = _category

    private var _difficulty = MutableLiveData<String>()
    var difficulty : MutableLiveData<String> = _difficulty

    fun getCategory(category: String){
        _category.value = category
    }

    fun getDifficulty(difficulty: String){
        _difficulty.value = difficulty
    }

    private var _arrayMoves = mutableListOf<LatLng>()
    var arrayMoves : MutableList<LatLng> = _arrayMoves

    private var _selectedLatPointA = MutableLiveData<Double>(0.0)
    var selectedLatPointA : MutableLiveData<Double> = _selectedLatPointA

    private var _selectedLngPointA = MutableLiveData<Double>(0.0)
    var selectedLngPointA : MutableLiveData<Double>  = _selectedLngPointA

    private var _selectedLatPointB = MutableLiveData<Double>(0.0)
    var selectedLatPointB : MutableLiveData<Double> = _selectedLatPointB

    private var _selectedLngPointB = MutableLiveData<Double>(0.0)
    var selectedLngPointB : MutableLiveData<Double> = _selectedLngPointB

    private var _mapLoadingMovement = MutableLiveData<Boolean>(true)
    var mapLoadingMovement : MutableLiveData<Boolean> = _mapLoadingMovement

    private var _duration = MutableLiveData<Long>(0)
    var duration : MutableLiveData<Long> = _duration

    fun RecA(lat: Double,lng: Double){
        _selectedLatPointA.value = lat
        _selectedLngPointA.value = lng
    }

    fun RecB(lat: Double,lng: Double){
        _selectedLatPointB.value = lat
        _selectedLngPointB.value = lng
    }

    @SuppressLint("MissingPermission")
    fun RouteRecord(
        fusedLocationClient: FusedLocationProviderClient,
    ){
        viewModelScope.launch {
            mapLoadingMovement.value = true
            withContext(Dispatchers.IO) {
                Log.d("State Loop","${mapLoadingMovement.value}")
                while(mapLoadingMovement.value == true){
                    Thread.sleep(5000)
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            location?.let { location ->
                                Log.d("Location","Latitude:${location.latitude},Longitude:${location.longitude}")
                                arrayMoves.add(LatLng(location.latitude,location.longitude))
                                durationSave(5000)
                            }
                        }
                }
            }
        }
    }

    private fun getTime(): String? {
        val myDateObj = LocalDateTime.now()
        val myFormatObj: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        return myDateObj.format(myFormatObj)
    }

    private fun getDistance(): Double {
        return SphericalUtil.computeLength(arrayMoves)
    }

    private fun durationSave(durationSave : Long) {
        duration.value = duration.value?.plus(durationSave)
    }

    fun MapRecordLatLng(navigationController: NavHostController, userViewModel:UserViewModel) {
        Log.d("Lat_A_Final","${_selectedLatPointA.value}")
        Log.d("Lng_A_Final","${_selectedLngPointA.value}")
        Log.d("Lat_B_Final","${_selectedLatPointB.value}")
        Log.d("Lng_B_Final","${_selectedLngPointB.value}")
        Log.d("Duration","${duration.value!!/60000}")
        Log.d("Distance","${getDistance().toInt()}")
        mapLoadingMovement.value = false
        viewModelScope.launch {
            Log.d("State Loop","${mapLoadingMovement.value}")
            val map = mapModelFactory(
                email = userViewModel.email.value!!,
                date = "${getTime()}",
                name = routeName.value!!,
                category = category.value!!,
                distance = getDistance().toInt(),
                difficulty = difficulty.value!!,
                duration = duration.value!!/60000,
                description = description.value!!,
                privacy = "public",
                photo = listOf(""),
                lat_A = selectedLatPointA.value!!,
                lng_A = selectedLngPointA.value!!,
                lat_B = selectedLatPointB.value!!,
                lng_B = selectedLngPointB.value!!,
                rec_movement = arrayMoves
            )
            val result = mapUseCase(map)
            if(result) {
                Log.d("Mensaje", "Post Ruta")
                navigationController.navigate(route = Routes.Home.route)
            }
        }
    }
}