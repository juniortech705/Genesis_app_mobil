package cd.zgeniuscoders.genesis.di

import cd.zgeniuscoders.genesis.authentication.data.services.FakeAuthenticationService
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationApi
import cd.zgeniuscoders.genesis.authentication.domain.services.AuthenticationService
import cd.zgeniuscoders.genesis.authentication.domain.usecases.UserInteractor
import cd.zgeniuscoders.genesis.authentication.ui.presentation.forgot.ForgotPasswordViewModel
import cd.zgeniuscoders.genesis.authentication.ui.presentation.login.LoginViewModel
import cd.zgeniuscoders.genesis.authentication.ui.presentation.opt_verification.OptVerificationViewModel
import cd.zgeniuscoders.genesis.authentication.ui.presentation.register.RegisterViewModel
import cd.zgeniuscoders.genesis.authentication.ui.presentation.reset.ResetPasswordViewModel
import cd.zgeniuscoders.genesis.core.data.services.SessionService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val userModule = module {

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<AuthenticationApi> {
        val moshi = get<Moshi>()

        Retrofit.Builder()
            .baseUrl("http://10.224.196.155:8000")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AuthenticationApi::class.java)

    }

    single<AuthenticationService> {
        val api = get<AuthenticationApi>()
        val context = androidContext()

//        AuthenticationServiceImpl(api, context)
        FakeAuthenticationService()
    }

    single<UserInteractor> {

        val serviceImpl = get<AuthenticationService>()

        UserInteractor.build(serviceImpl)
    }

    single<SessionService> {
        SessionService(androidContext())
    }

    viewModelOf(::RegisterViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::OptVerificationViewModel)


}