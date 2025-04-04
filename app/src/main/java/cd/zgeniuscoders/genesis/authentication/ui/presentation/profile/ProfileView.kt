package cd.zgeniuscoders.genesis.authentication.ui.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.authentication.ui.presentation.profile.components.ProfileHeader
import cd.zgeniuscoders.genesis.authentication.ui.presentation.profile.components.SettingItem
import cd.zgeniuscoders.genesis.common.ui.views.components.TopBarWithBackButton
import cd.zgeniuscoders.genesis.core.domain.utils.Routes


@Composable
fun ProfileView(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            TopBarWithBackButton(
                navHostController = navHostController,
                title = "Profile",
                isActionButtonShow = false,
                {}, {}
            )
        }
    ) { innerP ->
        Column(
            modifier = Modifier
                .padding(innerP)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 20.dp)
        ) {

            ProfileHeader(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingItem("Changer mon nom d'utilisateur", Icons.Filled.Person, {})
                SettingItem("Changer mon mot de passe", Icons.Filled.Lock, {})
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingItem("Appareils précédemment connectés", Icons.Filled.BluetoothConnected, {})
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                SettingItem("Se deconnecter", Icons.Filled.Logout, {}, showEndIcon = false)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfilePreview(modifier: Modifier = Modifier) {
    ProfileView(navHostController = rememberNavController())
}