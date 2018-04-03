package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen

import android.util.Log
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase


class RegistrationFinishPresenter {
    private val view: RegistrationFinishView
    private val registerUseCase: RegisterUseCase

    constructor(view: RegistrationFinishView, registerUseCase: RegisterUseCase) {
        this.view = view
        this.registerUseCase = registerUseCase
        Log.d("myLogs", "Презентер создан")
    }

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
}