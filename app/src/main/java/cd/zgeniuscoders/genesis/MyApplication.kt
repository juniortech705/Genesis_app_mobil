package cd.zgeniuscoders.genesis

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import cd.zgeniuscoders.genesis.di.remoteModule
import cd.zgeniuscoders.genesis.di.userModule
import cd.zgeniuscoders.genesis.remote_controle.data.services.NotificationService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(applicationContext)
            modules(
                userModule,
                remoteModule
            )
        }

        createNotificationChannel()


    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            NotificationService.CHANNEL_ID,
            NotificationService.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.description = "Genesis Notification Channel"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}