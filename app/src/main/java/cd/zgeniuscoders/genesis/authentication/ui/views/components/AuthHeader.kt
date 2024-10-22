package cd.zgeniuscoders.genesis.authentication.ui.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cd.zgeniuscoders.genesis.R

@Composable
fun AuthHeader(
    title: String,
    subtitle: String
) {
    Image(
        painter = painterResource(id = R.drawable.logo_genesis),
        contentDescription = "genesis logo",
        modifier = Modifier.size(100.dp)
    )

    Text(title, fontSize = 24.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)

    Text(
        subtitle,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 5.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

}