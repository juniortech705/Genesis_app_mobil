package cd.zgeniuscoders.genesis.authentication.domain.usecases

import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService

class UserInteractor private constructor(
    val loginUseCase: LoginUseCase,
    val registerUseCase: RegisterUseCase,
    val resetPasswordUseCase: ResetPasswordUseCase,
    val forgotPasswordUseCase: ForgotPasswordUseCase,
    val logoutUseCase: LogoutUseCase,
    val verifyOptUsecase: VerifyOptUsecase
) {
    companion object {
        fun build(service: AuthenticationService): UserInteractor {
            return UserInteractor(
                LoginUseCase(service),
                RegisterUseCase(service),
                ResetPasswordUseCase(service),
                ForgotPasswordUseCase(service),
                LogoutUseCase(service),
                VerifyOptUsecase(service)
            )
        }
    }
}