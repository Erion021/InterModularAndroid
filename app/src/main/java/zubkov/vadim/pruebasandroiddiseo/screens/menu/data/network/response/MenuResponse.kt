package zubkov.vadim.pruebasandroiddiseo.screens.menu.data.network.response

import com.google.gson.annotations.SerializedName
import zubkov.vadim.pruebasandroiddiseo.screens.menu.data.dto.MenuDTO
import zubkov.vadim.pruebasandroiddiseo.screens.menu.domin.entity.MenuModel

data class MenuResponse(
    @SerializedName("data") val data: List<MenuDTO>,
    @SerializedName("status") val status: String
)