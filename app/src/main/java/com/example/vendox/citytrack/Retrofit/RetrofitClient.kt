package com.example.vendox.citytrack.Retrofit

import com.example.vendox.citytrack.*
import com.example.vendox.citytrack.Retrofit.Request.LoginRequest
import com.example.vendox.citytrack.Retrofit.Request.RegisterRequest
import com.example.vendox.citytrack.Retrofit.Response.RegisterResponse
import com.example.vendox.citytrack.Retrofit.Response.UserResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitClient {

    @POST("/login")
    fun getUserData(@Body request: LoginRequest): Observable<UserResponse>

    @POST("/register")
    fun registration(@Body request: RegisterRequest): Observable<Response<Void>>

    companion object  {
        fun create(): RetrofitClient {

            val okHttpBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpBuilder.addInterceptor(httpLoggingInterceptor)
            }

            okHttpBuilder.addInterceptor({
                val request = it.request()
                val url = request.url().newBuilder()
                        .build()
                it.proceed(request.newBuilder().url(url).build())
            })

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://private-08661-citytrack.apiary-mock.com/")
                    .client(okHttpBuilder.build())
                    .build()

            return retrofit.create(RetrofitClient::class.java);
        }
    }
}