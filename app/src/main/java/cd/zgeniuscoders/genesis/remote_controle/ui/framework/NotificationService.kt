package cd.zgeniuscoders.genesis.remote_controle.ui.framework

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import cd.zgeniuscoders.genesis.R
import cd.zgeniuscoders.genesis.MainActivity

class NotificationService(
    private val context: Context,
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String) {


        val activityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Genesis Notification")
            .setSmallIcon(R.drawable.ic_bluetooth_searching_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        notificationManager.notify(1, notification.build())


    }

    companion object {
        const val CHANNEL_ID = "not_channel"
        const val CHANNEL_NAME = "Genesis Notification Channel"
    }
}