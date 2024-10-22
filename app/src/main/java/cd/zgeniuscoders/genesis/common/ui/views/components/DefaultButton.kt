package cd.zgeniuscoders.genesis.common.ui.views.components


import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = RoundedCornerShape(8.dp),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(text, color = Color.White)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultButtonPreview() {
    DefaultButton(text = "Login")
}