package cd.zgeniuscoders.genesis.remote_controle.ui.view.home.components

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.genesis.remote_controle.domain.models.BluetoothMessage
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.HomeState

@Composable
fun HomeCardBanner(state: HomeState, messages: List<BluetoothMessage>, isConnected: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (state.isPlaying) {
                        Text(
                            state.timerText,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineLarge,

                            )
                    } else {
                        if (!isConnected) {
                            Text(
                                "Vous n'êtes pas connecté à un réchaud",
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium
                            )
                        } else {
                            Text(
                                "Vous êtes maintenant connecté",
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                item {
                    StoveInfo(
                        value = "${state.powerLevel}",
                        title = "Puissance",
                        icon = Icons.Default.FlashOn
                    )
                }
                item {
                    val regex = """([-+]?\d+\.\d{2})\s?°C""".toRegex()
                    var temperature = "0"
                    messages.forEach { message ->

                        val matchResult = regex.find(message.message)
                        if (matchResult != null) {
                            temperature = matchResult.groups[1]?.value.toString()
                        }

                    }



                    StoveInfo(
                        value = temperature,
                        title = "Température",
                        icon = Icons.Default.Thermostat
                    )
                }
            }

        }
    }
}

@Composable
fun StoveInfo(
    value: String,
    title: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(value)
            Text(title, style = MaterialTheme.typography.labelMedium)
        }
    }
}