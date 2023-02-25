package zubkov.vadim.pruebasandroiddiseo.screens.mapscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import zubkov.vadim.pruebasandroiddiseo.screens.login.ui.UserViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.BottomBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.TopBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import zubkov.vadim.pruebasandroiddiseo.screens.users.ui.PersonViewModel

@SuppressLint("MissingPermission", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GoogleMapsView(navigationController: NavHostController, mapViewModel: MapViewModel) {
    Scaffold(
        //topBar = { TopBarContent() },
        //bottomBar = { BottomBarContent(navigationController = navigationController) }
    ) {
        val context : Context = LocalContext.current
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    mapViewModel.selectedLatPointA.value!!,
                    mapViewModel.selectedLngPointA.value!!
                ), 15.2f
            )
        }
        GoogleMap(
            properties = mapViewModel.properties,
            uiSettings = mapViewModel.uiSettings,
            cameraPositionState = cameraPosition
        )
        Column(
            modifier = Modifier
                .padding(start = 110.dp, top = 570.dp)
        ) {
            Button(
                onClick = {
                    mapViewModel.mapLoadingMovement.value = false
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            location?.let { location ->
                                Log.d("Lat B", "${location.latitude}")
                                Log.d("Lng B", "${location.longitude}")
                                mapViewModel.RecB(location.latitude,location.longitude)
                                mapViewModel.arrayMoves.add(LatLng(location.latitude,location.longitude))
                                for(i in 0 until mapViewModel.arrayMoves.size){
                                    Log.d("ARRAY: ","${mapViewModel.arrayMoves[i]}")
                                    mapViewModel.arrayMoves[i]
                                }
                                navigationController.navigate(Routes.GmapPost.route)
                            }
                        }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(24.dp))
            ) {
                RecordEnd()
            }
        }
    }
}

@Composable
fun RecordEnd(){
    Text(text = "Terminar Grabacion")
}