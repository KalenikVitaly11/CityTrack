package com.example.vendox.citytrack.Presentation.View.Authorization.Registration.SecondScreen

import android.util.Log
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import com.example.vendox.citytrack.Domain.UseCases.RegisterUseCase


class RegistrationFinishPresenter {
    private val view: RegistrationFinishView
    private val registerUseCase: RegisterUseCase

    constructor(view: RegistrationFinishView, registerUseCase: RegisterUseCase) {
        this.view = view
        this.registerUseCase = registerUseCase
        Log.d("myLogs", "Презентер создан")
    }

    fun register(emailRegistraion: EmailRegistraion) {
        this.registerUseCase.registerEmail(emailRegistraion)
                .subscribe({ result ->
                    Log.d("myLogs", result.toString())
                    view.registrationSuccess()
                },
                        { throwable ->
                            Log.d("myLogs", throwable.message)
                            view.registrationError()
                        })
    }

}