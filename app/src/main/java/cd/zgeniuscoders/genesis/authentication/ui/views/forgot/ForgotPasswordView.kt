package cd.zgeniuscoders.genesis.authentication.ui.views.forgot

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.R
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.authentication.ui.views.components.AuthHeader
import cd.zgeniuscoders.genesis.authentication.ui.views.components.TextFieldComponent
import cd.zgeniuscoders.genesis.common.ui.views.theme.GenesisTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordView(navHostController: NavHostController) {
    Scaffold {
        val vm = getViewModel<ForgotPasswordViewModel>()
        val state = vm.state
        val onEvent = vm::onTriggerEvent

        LaunchedEffect(state.isLogged) {
            if (state.isLogged) {
                navHostController.navigate(Routes.opt_verification.route)
            }
        }

        ForgotPasswordBody(state, onEvent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordBody(
    state: ForgotPasswordState,
    onEvent: (event: ForgotPasswordEvent) -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    Scaffold {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            AuthHeader(
                title = "Mot de passe oublié ?",
                subtitle = "Entrez l'adresse e-mail associée à votre compte. Nous vous enverrons un lien pour réinitialiser votre mot de passe."
            )


            if (state.error.isNotBlank()) {
                Text(
                    state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            TextFieldComponent(
                textValue = state.email,
                label = "Email",
                keyboardType = KeyboardType.Email,
                content = {
                    Icon(Icons.Filled.Email, contentDescription = null)
                },
                onValueChange = {
                    onEvent(ForgotPasswordEvent.EmailChange(it))
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    onEvent(ForgotPasswordEvent.handlerSendEmail)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                    return@Button
                }
                Text("Envoyer le lien de réinitialisation")
            }
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ForgotPasswordPreview() {
    GenesisTheme {
        Scaffold {
            ForgotPasswordBody(
                state = ForgotPasswordState()
            )
        }
    }
}