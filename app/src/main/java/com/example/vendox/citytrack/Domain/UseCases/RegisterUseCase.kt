package com.example.vendox.citytrack.Domain.UseCases

import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.DataClasses.Response.SocNetRegistrationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response


class RegisterUseCase(private val mRepository: AuthRepository) {
    fun registerEmail(emailRegistration: EmailRegistration) : Observable<ResponseBody>{
        return mRepository.registerEmail(emailRegistration)
    }

    fun registerVk(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>{
        return mRepository.registerVk(registrationObject)
    }

    fun registerFb(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>{
        return mRepository.registerFb(registrationObject)
    }

}