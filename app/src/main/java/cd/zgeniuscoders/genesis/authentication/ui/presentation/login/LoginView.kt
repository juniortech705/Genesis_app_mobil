package cd.zgeniuscoders.genesis.authentication.ui.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.core.domain.utils.Routes
import cd.zgeniuscoders.genesis.authentication.ui.presentation.components.AuthHeader
import cd.zgeniuscoders.genesis.authentication.ui.presentation.components.TextFieldComponent
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginView(navHostController: NavHostController) {
    val vm = getViewModel<LoginViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.isLogged) {
        if (state.isLogged) {
            navHostController.navigate(Routes.home.route) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    LoginBody(state = state, onEvent, navHostController)
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginBody(
    state: LoginState,
    onEvent: (event: LoginEvent) -> Unit = {},
    navHostController: NavHostController
) {

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(50.dp))
        AuthHeader(
            title = "Se connecter",
            subtitle = "Veuillez entrer vos identifiants pour accéder à votre compte."
        )

        if (state.hasFormError) {
            Text(
                state.error!!,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        TextFieldComponent(
            textValue = state.email,
            label = "Email",
            keyboardType = KeyboardType.Email,
            content = {
                Icon(Icons.Filled.Email, contentDescription = null)
            },
            onValueChange = {
                onEvent(LoginEvent.EmailChange(it))
            },
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextFieldComponent(
            textValue = state.password, label = "Mot de passe",
            keyboardType = KeyboardType.Password,
            isPasswordField = true,
            content = {
                Icon(Icons.Filled.Lock, contentDescription = null)
            }
        ) {
            onEvent(LoginEvent.PasswordChange(it))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Mot de passe Oublier ?",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navHostController.navigate(Routes.forgot_password.route)
                },
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                onEvent(LoginEvent.Login)
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
            Text("Connexion")
        }

        Spacer(modifier = Modifier.weight(1f))

    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@PreviewLightDark
@Composable
fun LoginViewPreview() {
    Scaffold {
        LoginBody(LoginState(), navHostController = rememberNavController())
    }
}