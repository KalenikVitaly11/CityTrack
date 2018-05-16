package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendNewPassword

import android.util.Log
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase

class SendNewPasswordPresenter(val view:SendNewPasswordView, val useCase: ForgotPasswordUseCase) {
    fun sendNewPassword(email:String, password:String){
        useCase.sendNewPassword(email, password)
                .subscribe({result ->
                    Log.d("myLogs", result.toString())
                    Log.d("myLogs", result.toString())
                    view.showSuccess()
                    view.returnToLogin()
                }, {throwable ->
                    Log.d("myLogs", throwable.message)
                    view.showError()
                })
    }
}