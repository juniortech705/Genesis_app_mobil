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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.R
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.authentication.ui.views.components.AuthHeader
import cd.zgeniuscoders.genesis.authentication.ui.views.components.TextFieldComponent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordView(navHostController: NavHostController) {
    Scaffold {
        val vm = getViewModel<ForgotPasswordViewModel>()
        val state = vm.state
        val onEvent = vm::onTriggerEvent

        val coroutineScope = rememberCoroutineScope()

        val submitForm: () -> Unit = {
            coroutineScope.launch {
                vm.sendEmail()
                if (!state.hasFormError) {
                    navHostController.navigate(Routes.reset_password.route)
                }
            }
        }

        ForgotPasswordBody(state, submitForm, onEvent)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordBody(
    state: ForgotPasswordState,
    submitForm: () -> Unit = {},
    onEvent: (event: ForgotPasswordEvent) -> Unit = {}
) {


    Scaffold {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_genesis),
                contentDescription = "genesis logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            AuthHeader(
                title = "Mot de passe oublié ?",
                subtitle = "Entrez l'adresse e-mail associée à votre compte. Nous vous enverrons un lien pour réinitialiser votre mot de passe."
            )

            if (state.hasFormError) {
                Text(
                    state.emailError,
                    color = Color.Red
                )
            }

            TextFieldComponent(
                textValue = state.email,
                label = "Email", shape = RoundedCornerShape(topStart = 15.dp, bottomEnd = 15.dp),
                keyboardType = KeyboardType.Email,
                content = {
                    Icon(Icons.Filled.Email, contentDescription = null)
                },
                onValueChange = {
                    onEvent(ForgotPasswordEvent.EmailChange(it))
                },
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    submitForm()
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
    Scaffold {
        ForgotPasswordBody(
            state = ForgotPasswordState()
        )
    }
}