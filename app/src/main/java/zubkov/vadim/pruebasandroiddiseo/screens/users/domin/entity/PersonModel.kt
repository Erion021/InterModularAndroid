package zubkov.vadim.pruebasandroiddiseo.screens.users.domin.entity

data class PersonModel(
    val name : String,
    val lastname : String,
    val email : String,
    val date : String,
    val nick : String,
    val following : List<String>,
    val photo : String,
    val description : String,
    val fav_routes: List<String>,
    val password : String
)

var nameOld = ""
var lastnameOld = ""
var emailCurrent = ""
var nick = ""
var date = ""
var following = listOf("")
var photo = ""
var descriptionOld = ""
var fav_routes = listOf("")
var passwordOld = ""