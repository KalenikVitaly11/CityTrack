package com.example.vendox.citytrack.Data.Repository

import com.example.vendox.citytrack.Data.AuthApi
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistration
import com.example.vendox.citytrack.Domain.DataClasses.Request.SocNetRegistrationRequest
import com.example.vendox.citytrack.Domain.DataClasses.Response.SocNetRegistrationResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response


class AuthRepositoryImpl : AuthRepository {
    override fun registerEmail(registrationObject: EmailRegistration): Observable<ResponseBody> {
        return AuthApi.create().registerEmail(registrationObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun registerFb(registrationObject: SocNetRegistrationRequest): Observable<Response<SocNetRegistrationResponse>> {
        return AuthApi.create().registerSocialNetwork(registrationObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun registerVk(registrationObject: SocNetRegistrationRequest): Observable<Response<SocNetRegistrationResponse>> {
        return AuthApi.create().registerSocialNetwork(registrationObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}