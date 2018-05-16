package com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendCode

import android.util.Log
import com.example.vendox.citytrack.Domain.UseCases.ForgotPasswordUseCase
import com.example.vendox.citytrack.Presentation.View.Authorization.ForgotPassword.SendEmail.SendEmailView

class SendCodePresenter (val view: SendCodeView, val useCase: ForgotPasswordUseCase) {
//    fun sendEmail(email:String){
//        useCase.sendEmail(email)
//                .subscribe({result ->
//                    Log.d("myLogs", result.toString())
//                    view.goToChangePassword()
//                }, {throwable ->
//                    Log.d("myLogs", throwable.message)
//                    view.showError()
//                })
//    }

    fun sendCode(email: String, code:String){
        useCase.sendCode(email, code)
                .subscribe({result ->
                    Log.d("myLogs", result.toString())
                    view.goToChangePassword()
                }, {throwable ->
                    Log.d("myLogs", throwable.message)
                    view.showError()
                })
    }
}