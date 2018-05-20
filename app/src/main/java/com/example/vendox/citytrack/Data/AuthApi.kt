package com.example.vendox.citytrack.Data

import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailLogin
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.ForgotPasswordObject
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.DataClasses.Response.EmailLoginResponse
import com.example.vendox.citytrack.Domain.DataClasses.Response.SocNetRegistrationResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("/register")
    fun registerEmail(@Body body: EmailRegistration): Observable<ResponseBody>

    @POST("/networks")
    fun registerSocialNetwork(@Body body:SocNetRegistrationRequest): Observable<Response<SocNetRegistrationResponse>>

    @POST("/pass-reset/sendemail")
    fun sendEmail(@Body body: ForgotPasswordObject): Observable<ResponseBody>

    @POST("/pass-reset/codeconfirm")
    fun sendCode(@Body body: ForgotPasswordObject): Observable<ResponseBody>

    @POST("/changepass")
    fun sendNewPassword(@Body body: EmailLogin): Observable<ResponseBody>
    @POST("/login")
    fun loginEmail(@Body body: EmailLogin):Observable<Response<EmailLoginResponse>>


    companion object {
        fun create(): AuthApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://citytrack.online/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return  retrofit.create(AuthApi::class.java)
        }
    }

}