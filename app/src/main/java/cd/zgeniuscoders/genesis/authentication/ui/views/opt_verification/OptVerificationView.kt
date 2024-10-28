package cd.zgeniuscoders.genesis.authentication.ui.views.opt_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.common.ui.views.components.DefaultButton
import cd.zgeniuscoders.genesis.authentication.ui.views.components.OptTextField
import cd.zgeniuscoders.genesis.common.ui.views.theme.GenesisTheme

@Composable
fun OptVerificationView(navHostController: NavHostController) {
    Scaffold { innerP ->
        Column(
            modifier = Modifier
                .padding(
                    vertical = innerP.calculateTopPadding(),
                    horizontal = 20.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Lock,
                contentDescription = "lock icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Entrez le code de vérification",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(40.dp))

            OptTextField()

            Spacer(modifier = Modifier.height(20.dp))

            DefaultButton(
                text = "Vérifier",
                onClick = {
                    navHostController.navigate(Routes.bottomNavBar.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Vous attendez toujours le code de vérification par E-mail?",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Cliquez ici pour renvoyer le code.",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview
fun OptVerificationPreview() {
    GenesisTheme {
        OptVerificationView(navHostController = rememberNavController())
    }
}