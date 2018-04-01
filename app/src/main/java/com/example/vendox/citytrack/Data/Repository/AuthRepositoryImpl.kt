package com.example.vendox.citytrack.Data.Repository

import com.example.vendox.citytrack.Data.AuthApi
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import java.util.*
import io.reactivex.Observable
import okhttp3.ResponseBody


class AuthRepositoryImpl : AuthRepository {
    override fun registerEmail(registrationObject: EmailRegistraion): Observable<ResponseBody> {
        return AuthApi.create().registerEmail(registrationObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}