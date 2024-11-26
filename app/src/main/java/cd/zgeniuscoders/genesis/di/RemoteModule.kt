package cd.zgeniuscoders.genesis.di

import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.framework.SessionService
import cd.zgeniuscoders.genesis.authentication.ui.views.opt_verification.OptVerificationViewModel
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareViewModel
import cd.zgeniuscoders.genesis.remote_controle.domain.services.RemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.domain.use_case.RemoteControleInteractor
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.BluetoothRemoteControlServiceImpl
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.FakeBluetoothRemoteControlService
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.NotificationService
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.bluetooth.AndroidBluetoothController
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.HomeViewModel
import cd.zgeniuscoders.genesis.remote_controle.ui.view.pairedDevice.PairedDeviceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteModule = module {

    single<RemoteControlService> {
        val appContext = androidContext()
        BluetoothRemoteControlServiceImpl(appContext)
//        FakeBluetoothRemoteControlService()
    }

    single<RemoteControleInteractor> {
        RemoteControleInteractor.build(get<RemoteControlService>())
    }


    single<ShareViewModel> {
        ShareViewModel(
            get<RemoteControleInteractor>(),
            get<UserInteractor>(),
            get<SessionService>(),
        )
    }


    viewModel {
        val appContext = androidContext()
        val notification = NotificationService(appContext)

        HomeViewModel(appContext, notification)
    }

    viewModel<PairedDeviceViewModel> {
        PairedDeviceViewModel()
    }

    viewModel<OptVerificationViewModel> {
        OptVerificationViewModel(get(), get())
    }


}