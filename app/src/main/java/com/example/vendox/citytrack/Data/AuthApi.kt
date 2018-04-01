package com.example.vendox.citytrack.Data

import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import io.reactivex.Observable
import okhttp3.ResponseBody
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("/register")
    fun registerEmail(@Body body: EmailRegistraion): Observable<ResponseBody>


    companion object {
        fun create(): AuthApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://private-08661-citytrack.apiary-mock.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return  retrofit.create(AuthApi::class.java)
        }
    }

}