package com.example.vendox.citytrack.Data.Repository

import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import java.util.*
import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody


interface AuthRepository {
    fun registerEmail(registrationObject: EmailRegistraion):Observable<ResponseBody>
}