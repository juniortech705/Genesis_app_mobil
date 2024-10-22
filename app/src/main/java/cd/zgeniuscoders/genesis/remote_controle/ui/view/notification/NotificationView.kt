package cd.zgeniuscoders.genesis.remote_controle.ui.view.notification

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cd.zgeniuscoders.genesis.remote_controle.ui.view.notification.components.NotificationItem


@Composable
fun NotificationView() {
    Scaffold { innerP ->
        LazyColumn(
            modifier = Modifier.padding(innerP)
        ) {
            item{
                NotificationItem()
            }
        }
    }
}

@Composable
@Preview
fun NotificationPreview() {
    NotificationView()
}