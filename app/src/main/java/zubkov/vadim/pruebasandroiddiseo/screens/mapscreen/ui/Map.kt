package zubkov.vadim.pruebasandroiddiseo.screens.mapscreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.ui.MapViewModel
import zubkov.vadim.pruebasandroiddiseo.screens.models.BottomBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.TopBarContent
import zubkov.vadim.pruebasandroiddiseo.screens.models.navigation.Routes
import javax.inject.Singleton

@Singleton
@SuppressLint("MissingPermission", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapaGoogle(navigationController: NavHostController, mapViewModel: MapViewModel) {
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val context : Context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    Scaffold(
        topBar = { TopBarContent() },
        bottomBar = { BottomBarContent(navigationController = navigationController)}
    ) {
        if (locationPermissionState.status.isGranted){
            val cameraPosition = rememberCameraPositionState{
                position = CameraPosition.fromLatLngZoom(
                    LatLng(
                        39.4637,

                        -3.1492
                    ), 5.2f
                )
            }
            GoogleMap(
                properties = mapViewModel.properties,
                uiSettings = mapViewModel.uiSettings,
                cameraPositionState = cameraPosition
            )
            Column(
                modifier = Modifier
                    .padding(start = 110.dp,top = 470.dp)
            ){
                Button(
                    onClick = {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                location?.let { location ->
                                    Log.d("Lat A","${location.latitude}")
                                    Log.d("Lng A","${location.longitude}")
                                    mapViewModel.RecA(location.latitude,location.longitude)
                                    mapViewModel.arrayMoves.add(LatLng(location.latitude,location.longitude))
                                    mapViewModel.RouteRecord(fusedLocationClient)
                                    navigationController.navigate(Routes.GmapView.route)
                                }
                            }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(80.dp)
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(24.dp))
                ) {
                    Record()
                }
            }
        } else {
            LaunchedEffect(locationPermissionState.status.isGranted){
                locationPermissionState.launchPermissionRequest()
            }
        }
    }
}

@Composable
fun Record(){
    Text(text = "Grabar Ruta")
}