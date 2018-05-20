package com.example.vendox.citytrack.Data.Repository

import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.ForgotPasswordObject
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.DataClasses.Response.EmailLoginResponse
import com.example.vendox.citytrack.Domain.DataClasses.Response.SocNetRegistrationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response


interface AuthRepository {
    fun registerEmail(registrationObject: EmailRegistration):Observable<ResponseBody>
    fun loginEmail(loginObject: EmailLogin):Observable<Response<EmailLoginResponse>>
    fun registerVk(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>
    fun registerFb(registrationObject: SocNetRegistrationRequest):Observable<Response<SocNetRegistrationResponse>>
    fun forgotPasswordSendEmail(forgotPasswordObject:ForgotPasswordObject): Observable<ResponseBody>
    fun forgotPasswordSendCode(forgotPasswordObject:ForgotPasswordObject): Observable<ResponseBody>
    fun sendNewPassword(emailLogin: EmailLogin): Observable<ResponseBody>
}