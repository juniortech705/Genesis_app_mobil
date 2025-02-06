package cd.zgeniuscoders.genesis.authentication.ui.presentation.opt_verification

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import cd.zgeniuscoders.genesis.common.ui.views.components.DefaultButton
import cd.zgeniuscoders.genesis.authentication.ui.presentation.components.OptTextField
import cd.zgeniuscoders.genesis.common.ui.views.theme.GenesisTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun OptVerificationView(navHostController: NavHostController) {
    val vm = koinViewModel<OptVerificationViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.isLogged) {
        if (state.isLogged) {
            navHostController.navigate(Routes.reset_password.route) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    OptVerificationBody(state, onEvent)
}

@Composable
fun OptVerificationBody(
    state: OptVerificationState,
    onEvent: (event: OptVerificationEvent) -> Unit = {}
) {
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

            if (state.error.isNotBlank()) {
                Text(
                    state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OptTextField(state.code) {
                onEvent(OptVerificationEvent.OnCodeChange(it))
            }

            Spacer(modifier = Modifier.height(10.dp))

            DefaultButton(
                text = "Vérifier",
                onClick = {
                    onEvent(OptVerificationEvent.HandlerSendCod)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

        }
    }
}

@Composable
@Preview
fun OptVerificationPreview() {
    GenesisTheme {
        OptVerificationBody(OptVerificationState())
    }
}