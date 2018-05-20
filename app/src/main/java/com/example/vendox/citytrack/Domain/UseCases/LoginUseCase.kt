package com.example.vendox.citytrack.Domain.UseCases

import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.DataClasses.Response.EmailLoginResponse
import retrofit2.Response
import java.util.*
import io.reactivex.Observable

class LoginUseCase(private val mRepository: AuthRepository) {
    fun loginEmail(emailLogin: EmailLogin):Observable<Response<EmailLoginResponse>>{
        return mRepository.loginEmail(emailLogin)
    }
}