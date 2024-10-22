package cd.zgeniuscoders.genesis.di

import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.framework.SessionService
import cd.zgeniuscoders.genesis.common.ui.views.main.ShareViewModel
import cd.zgeniuscoders.genesis.remote_controle.domain.services.BluetoothController
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.NotificationService
import cd.zgeniuscoders.genesis.remote_controle.ui.framework.bluetooth.AndroidBluetoothController
import cd.zgeniuscoders.genesis.remote_controle.ui.view.home.HomeViewModel
import cd.zgeniuscoders.genesis.remote_controle.ui.view.pairedDevice.PairedDeviceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteModule = module {

    single<BluetoothController> {
        val appContext = androidContext()
        AndroidBluetoothController(appContext)
    }


    single<ShareViewModel> {
        val controller = get<BluetoothController>()
        ShareViewModel(controller, get<UserInteractor>(), get<SessionService>())
    }


    viewModel {
        val appContext = androidContext()
        val notification = NotificationService(appContext)

        HomeViewModel(appContext, notification)
    }

    viewModel<PairedDeviceViewModel> {
        PairedDeviceViewModel()
    }


}