package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.repository

import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.service.MenuService
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val api : MenuService
) {
    suspend fun getRouteRepository():List<MenuDTO>{
        return api.getRoutes()
    }

    suspend fun likeRoute(email:String,ruta:String){
        api.likeRoute(email,ruta)
    }

    suspend fun unlikeRoute(email:String, ruta:String){
        api.unlikeRoute(email,ruta)
    }

    suspend fun getRouteEmailRepository(email : String):List<MenuDTO>{
        return api.getRoutesByEmail(email)
    }
}