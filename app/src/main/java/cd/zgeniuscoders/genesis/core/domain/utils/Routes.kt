package cd.zgeniuscoders.genesis.core.domain.utils

sealed class Routes(val route: String) {
    data object bottomNavBar : Routes("bottom_nav")
    data object splash_screen : Routes("splash_screen")
    data object login : Routes("login_screen")
    data object register : Routes("register_screen")
    data object forgot_password : Routes("forgot_password")
    data object reset_password : Routes("reset_password")
    data object home : Routes("home")
    data object profile : Routes("profile")

    data object connectDevice: Routes("connect_device")
    data object pairedDevice : Routes("pair_device")
    data object controlDevice: Routes("control_device")

    data object opt_verification : Routes("opt_verication")
}