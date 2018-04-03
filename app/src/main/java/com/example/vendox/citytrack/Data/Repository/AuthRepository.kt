package com.example.vendox.citytrack.Data.Repository

import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.DataClasses.Response.SocNetRegistrationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response


interface AuthRepository {
    fun registerEmail(registrationObject: EmailRegistration):Observable<ResponseBody>
    fun registerVk(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>
    fun registerFb(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>
}