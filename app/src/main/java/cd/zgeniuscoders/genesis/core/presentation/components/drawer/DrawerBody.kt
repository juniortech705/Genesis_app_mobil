package cd.zgeniuscoders.genesis.common.ui.views.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareEvent
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import kotlinx.coroutines.launch


@Composable
fun DrawerBody(
    navHostController: NavHostController,
    drawerState: DrawerState,
    onEvent: (event: ShareEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        DrawerMenuItem(onClick = { /*TODO*/ }, icon = Icons.Default.Person, title = "Profile")
        DrawerMenuItem(onClick = { /*TODO*/ }, icon = Icons.Default.Contacts, title = "Contact")
        DrawerMenuItem(
            onClick = { /*TODO*/ },
            icon = Icons.Default.PrivacyTip,
            title = "Politique de confidentialité"
        )
        Spacer(modifier = Modifier.weight(1f))
        DrawerMenuItem(onClick = {
            scope.launch {

                onEvent(ShareEvent.OnLogout)

                drawerState.apply {
                    close()
                }

                navHostController.navigate(Routes.login.route) {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }, icon = Icons.Default.Logout, title = "Se deconnecter")
    }

}