package cd.zgeniuscoders.genesis.remote_controle.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeBody(){
    Column {
        Text("Hello")
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true)
fun HomeBodyPreview(){
    Scaffold {
        HomeBody()
    }
}