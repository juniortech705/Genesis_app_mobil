package cd.zgeniuscoders.genesis.authentication.ui.views.register

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.genesis.common.utility.Routes
import cd.zgeniuscoders.genesis.authentication.ui.views.components.AuthHeader
import cd.zgeniuscoders.genesis.authentication.ui.views.components.TextFieldComponent
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterView(navHostController: NavHostController) {

    val vm = getViewModel<RegisterViewModel>()
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

    RegisterBody(state, onEvent, navHostController)

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterBody(
    state: RegisterState,
    onEvent: (event: RegisterEvent) -> Unit = {},
    navHostController: NavHostController
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            AuthHeader(
                title = "Créer un compte",
                subtitle = "Remplissez les champs ci-dessous pour créer votre compte."
            )

            if (state.hasFormError) {
                Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextFieldComponent(
                textValue = state.email,
                label = "Email",
                keyboardType = KeyboardType.Email,
                content = {
                    Icon(Icons.Filled.Email, contentDescription = null)
                },
                onValueChange = {
                    onEvent(RegisterEvent.EmailChange(it))
                },
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextFieldComponent(
                textValue = state.username,
                label = "Nom d'utilisateur",
                keyboardType = KeyboardType.Text,
                content = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                onValueChange = {
                    onEvent(RegisterEvent.UsernameChange(it))
                },
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextFieldComponent(
                textValue = state.password,
                label = "Mot de passe",
                keyboardType = KeyboardType.Password,
                content = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                onValueChange = {
                    onEvent(RegisterEvent.PasswordChange(it))
                },
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextFieldComponent(
                textValue = state.confirmPassword,
                label = "Confirmer le Mot de passe",
                keyboardType = KeyboardType.Password,
                content = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                onValueChange = {
                    onEvent(RegisterEvent.ConfirmPasswordChange(it))
                },
                isPasswordField = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onEvent(RegisterEvent.OnRegister)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Créer mon compte")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navHostController.navigate(Routes.login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Se conncter")
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    RegisterBody(RegisterState(), navHostController = rememberNavController())
}