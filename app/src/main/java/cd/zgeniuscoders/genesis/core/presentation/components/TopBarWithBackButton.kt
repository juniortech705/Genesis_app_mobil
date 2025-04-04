package cd.zgeniuscoders.genesis.common.ui.views.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBackButton(
    navHostController: NavHostController,
    title: String,
    isActionButtonShow: Boolean = true,
    startScan: () -> Unit,
    stopScan: () -> Unit
) {
    val isDarkMode = isSystemInDarkTheme()

    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (isDarkMode) Color.White else Color.Black
            )
        },
        navigationIcon = {

            IconButton(
                onClick = { navHostController.popBackStack() },
                modifier = Modifier.padding(0.dp),
            ) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = null, tint = contentColor)
            }

        },
        actions = {
            if (isActionButtonShow) {
                IconButton(
                    onClick = { startScan() }
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = null, tint = contentColor)
                }
                IconButton(
                    onClick = { stopScan() }
                ) {
                    Icon(Icons.Filled.Stop, contentDescription = null, tint = contentColor)
                }
            }
        })
}
