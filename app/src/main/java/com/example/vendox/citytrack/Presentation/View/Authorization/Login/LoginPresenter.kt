package com.example.vendox.citytrack.Presentation.View.Authorization.Login

import android.util.Log
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.LoginUseCase
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen.RegistrationFinishView

/**
 * Created by Vitaly on 03.04.2018.
 */
class LoginPresenter(private val view: LoginView,
                     private val registerUseCase: RegisterUseCase,
                     private val loginUseCase: LoginUseCase) {

    fun registerEmail(emailRegistration: EmailRegistration) {
        this.registerUseCase.registerEmail(emailRegistration)
                .subscribe({ result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, { throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }

    fun loginEmail(emailLogin: EmailLogin) {
        this.loginUseCase.loginEmail(emailLogin)
                .subscribe({ result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, { throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }

    fun registerVk(socNetRegistrationRequest: SocNetRegistrationRequest) {
        this.registerUseCase.registerVk(socNetRegistrationRequest)
                .subscribe({ result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, { throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }

    fun registerFb(socNetRegistrationRequest: SocNetRegistrationRequest) {
        this.registerUseCase.registerFb(socNetRegistrationRequest)
                .subscribe({ result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, { throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }
}