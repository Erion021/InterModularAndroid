package zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.usecase

import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.repository.MenuRepository
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import javax.inject.Inject

class MenuUseCase @Inject constructor(
    private val repository : MenuRepository
) {
    suspend operator fun invoke() : List<MenuDTO>{
        return repository.getRouteRepository()
    }

    suspend fun likeRoute(email:String, idRuta : String){
        repository.likeRoute(email,idRuta)
    }

    suspend fun unlikeRoute(email:String, idRuta : String){
        repository.unlikeRoute(email,idRuta)
    }

    suspend fun getRouteByEmail(email : String) : List<MenuDTO>{
        return repository.getRouteEmailRepository(email)
    }
}