package cd.zgeniuscoders.genesis.common.ui.views.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Colors {

    val blue = Color(0xFF0099FF)
    val blueLight = Color(0xFF90D2FD)

    val Green200 = Color(0xFF82ECFF)
    val Green300 = Color(0xFF248ED6)
    val Green500 = Color(0xFF2196F3)

    val Red = Color(0xFFfc0808)
    val DarkColor2 = Color(0xFF202532)
    val PrimaryColor = Color(0xFF1466c3)
    val DarkColor = Color(0xFF101522)
    val LightColor = Color(0xFF414D66)
    val LightColor2 = Color(0xFFF1F3F8)

    val GreenGradient = Brush.linearGradient(
        colors = listOf(Green300, Green200),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    val DarkGradient = Brush.verticalGradient(
        colors = listOf(DarkColor2, DarkColor)
    )

}