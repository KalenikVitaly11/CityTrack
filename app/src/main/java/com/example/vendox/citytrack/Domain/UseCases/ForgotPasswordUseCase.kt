package com.example.vendox.citytrack.Domain.UseCases

import android.util.Log
import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.DataClasses.Request.ForgotPasswordObject
import io.reactivex.Observable
import okhttp3.ResponseBody

class ForgotPasswordUseCase(val authRepository: AuthRepository) {
    fun sendEmail(email: String): Observable<ResponseBody> {
        return authRepository.forgotPasswordSendEmail(ForgotPasswordObject(email, ""))
    }

    fun sendCode(email: String, code: String): Observable<ResponseBody> {
        return authRepository.forgotPasswordSendCode(ForgotPasswordObject(email, code))
    }

    fun sendNewPassword(email: String, password: String): Observable<ResponseBody> {
        return authRepository.sendNewPassword(EmailLogin(email, password))
    }
}