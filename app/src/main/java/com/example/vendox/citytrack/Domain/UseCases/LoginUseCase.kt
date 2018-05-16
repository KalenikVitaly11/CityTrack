package com.example.vendox.citytrack.Domain.UseCases

import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class LoginUseCase(val authRepository: AuthRepository) {
    fun loginEmail(loginObject:EmailLogin):Observable<Response<ResponseBody>> {
        return authRepository.loginEmail(loginObject)
    }
}