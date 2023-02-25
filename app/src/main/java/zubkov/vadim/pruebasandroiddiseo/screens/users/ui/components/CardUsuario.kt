package zubkov.vadim.pruebasandroiddiseo.screens.users.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import zubkov.vadim.pruebasandroiddiseo.R
import zubkov.vadim.pruebasandroiddiseo.screens.users.data.dto.PersonDTO

@Composable
@ExperimentalFoundationApi
fun CardUsuario(emailUser: String, navigationController:NavHostController) {
    Card(
        modifier = Modifier
            .padding(0.dp,0.dp,0.dp,1.dp)
            .fillMaxWidth()
            .clickable {
                navigationController.navigate("extUsers/${emailUser}")
            },
                elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(9.dp)

        ) {
            Image(
                painter = painterResource(R.drawable.fotoperfil),
                contentDescription = "Foto Perfil Usuario",
                modifier = Modifier
                    .padding(10.dp,0.dp,0.dp,0.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = emailUser,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                /*
                Text(
                    text = usuario.name + " "+ usuario.lastname,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                 */
            }
        }
    }
}