package cd.zgeniuscoders.genesis.di

import androidx.room.Room
import cd.zgeniuscoders.genesis.core.presentation.main.ShareViewModel
import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotDao
import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotDatabase
import cd.zgeniuscoders.genesis.remote_controle.data.services.ChatBotServiceImpl
import cd.zgeniuscoders.genesis.remote_controle.data.services.FakeBluetoothRemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.data.services.NotificationService
import cd.zgeniuscoders.genesis.remote_controle.data.services.SpeechRecognitionService
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ChatBotService
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.domain.use_case.RemoteControleInteractor
import cd.zgeniuscoders.genesis.remote_controle.presentation.chatbot.ChatBotViewModel
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeViewModel
import cd.zgeniuscoders.genesis.remote_controle.presentation.pairedDevice.PairedDeviceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val remoteModule = module {


    single<ChatBotDao> {

        val appContext = androidContext()
        Room.databaseBuilder(
            appContext,
            ChatBotDatabase::class.java,
            "chatbots"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
            .ChatBotDao()

    }

    single<RemoteControlService> {
        val appContext = androidContext()
//        BluetoothRemoteControlServiceImpl(appContext)
        FakeBluetoothRemoteControlService()
    }

    single<ChatBotService> {
        ChatBotServiceImpl(get())
    }
    single<RemoteControleInteractor> {
        RemoteControleInteractor.build(get<RemoteControlService>())
    }
    single<SpeechRecognitionService> {
        val appContext = androidContext()
        SpeechRecognitionService(appContext)
    }
    single<NotificationService> {
        val appContext = androidContext()
        NotificationService(appContext)
    }

    viewModelOf(::ChatBotViewModel)
    viewModelOf(::ShareViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PairedDeviceViewModel)


}