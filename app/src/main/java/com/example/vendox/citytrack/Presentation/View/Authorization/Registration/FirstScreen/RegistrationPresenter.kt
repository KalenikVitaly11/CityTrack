package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.FirstScreen

import android.util.Log
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase

/**
 * Created by Vitaly on 02.04.2018.
 */
class RegistrationPresenter {
    private val view: RegistrationView
    private val registerUseCase: RegisterUseCase

    constructor(view: RegistrationView, registerUseCase: RegisterUseCase) {
        this.view = view
        this.registerUseCase = registerUseCase
    }

    fun continueRegistration(){
        view.continueRegistration()
    }

    fun registerVk(socNetRegistrationRequest: SocNetRegistrationRequest) {
        this.registerUseCase.registerVk(socNetRegistrationRequest)
                .subscribe({result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, {throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }

    fun registerFb(socNetRegistrationRequest: SocNetRegistrationRequest) {
        this.registerUseCase.registerFb(socNetRegistrationRequest)
                .subscribe({result ->
                    Log.d("myLogs", result.toString())
                    view.goToMap()
                }, {throwable ->
                    Log.d("myLogs", throwable.message)
                    view.registrationError()
                })
    }
}