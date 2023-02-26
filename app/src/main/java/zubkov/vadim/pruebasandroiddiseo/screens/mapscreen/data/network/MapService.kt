package zubkov.vadim.pruebasandroiddiseo.screens.mapscreen.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity.MenuModel
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import javax.inject.Inject

class MapService @Inject constructor(
    private val mapClient: MapClient
){
    suspend fun doMaps(map : MenuDTO):Boolean{
        return withContext(Dispatchers.IO){
            val response = mapClient.doMaps(map)
            response.body()?.status?.compareTo("200") == 0
        }
    }
}