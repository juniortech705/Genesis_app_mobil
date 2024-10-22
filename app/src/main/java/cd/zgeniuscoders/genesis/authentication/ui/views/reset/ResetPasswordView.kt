package cd.zgeniuscoders.genesis.authentication.ui.views.reset

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.authentication.ui.views.components.AuthHeader
import cd.zgeniuscoders.genesis.authentication.ui.views.components.TextFieldComponent
import cd.zgeniuscoders.genesis.authentication.ui.views.login.LoginEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@Composable
fun ResetPasswordView(navHostController: NavHostController) {

    val vm = getViewModel<ResetPasswordViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    val coroutineScope = rememberCoroutineScope()

    val submitForm: () -> Unit = {
        coroutineScope.launch {
            vm.resestPassword()
            if (state.isValidated) {
                navHostController.navigate(Routes.login.route)
            }
        }
    }

    LaunchedEffect(state.isLogged) {
        if (state.isLogged) {
            navHostController.navigate(Routes.home.route) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    ResetPasswordBody(state, onEvent, submitForm)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResetPasswordBody(
    state: ResetPasswordState,
    onEvent: (event: ResetPasswordEvent) -> Unit = {},
    submitForm: () -> Unit = {}
) {
    Scaffold {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AuthHeader(title = "Réinitialiser votre mot de passe", subtitle = "Veuillez entrer votre nouveau mot de passe ci-dessous.")

            if (state.hasFormError) {
                Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            TextFieldComponent(
                textValue = state.password,
                label = "Nouveau Mot de passe",
                keyboardType = KeyboardType.Email,
                content = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                onValueChange = {
                    onEvent(ResetPasswordEvent.PasswordChange(it))
                },
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextFieldComponent(
                textValue = state.confirmPassword,
                label = "Confirmer  le mot de passe",
                keyboardType = KeyboardType.Email,
                content = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                onValueChange = {
                    onEvent(ResetPasswordEvent.ConfirmPasswordChange(it))
                },
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    submitForm()
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                    return@Button
                }
                Text("Réinitialiser le mot de passe")
            }

        }


    }
}

@Preview
@Composable
fun ResetPasswordViewPreview() {
    ResetPasswordBody(ResetPasswordState())
}
