package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendEmail

import android.util.Log
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase

class SendEmailPresenter(val view: SendEmailView, val useCase: ForgotPasswordUseCase) {
    fun sendEmail(email:String){
        useCase.sendEmail(email)
                .subscribe({result ->
                    Log.d("myLogs", result.toString())
                    view.goToSendCode()
                }, {throwable ->
                    Log.d("myLogs", throwable.message)
                    view.showError()
                })
    }

//    fun sendCode(email: String, code:String){
//        useCase.sendCode(email, code)
//                .subscribe({result ->
//                    Log.d("myLogs", result.toString())
//                }, {throwable ->
//                    Log.d("myLogs", throwable.message)
//                })
//    }
}