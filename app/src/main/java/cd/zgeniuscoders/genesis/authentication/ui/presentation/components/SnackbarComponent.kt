package cd.zgeniuscoders.genesis.authentication.ui.presentation.components

import android.content.Context
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun SnackbarComponent(snackbarHostState: SnackbarHostState, msg: String, context: Context? = null){
    SnackbarHost(hostState = snackbarHostState)
}