package cd.zgeniuscoders.genesis.di

import cd.zgeniuscoders.genesis.core.presentation.main.ShareViewModel
import cd.zgeniuscoders.genesis.remote_controle.data.services.BluetoothRemoteControlServiceImpl
import cd.zgeniuscoders.genesis.remote_controle.data.services.FakeBluetoothRemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.data.services.NotificationService
import cd.zgeniuscoders.genesis.remote_controle.data.services.SpeechRecognitionService
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.domain.use_case.RemoteControleInteractor
import cd.zgeniuscoders.genesis.remote_controle.presentation.home.HomeViewModel
import cd.zgeniuscoders.genesis.remote_controle.presentation.pairedDevice.PairedDeviceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val remoteModule = module {

    single<RemoteControlService> {
        val appContext = androidContext()
//        BluetoothRemoteControlServiceImpl(appContext)
        FakeBluetoothRemoteControlService()
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


    viewModelOf(::ShareViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PairedDeviceViewModel)


}