package com.example.vendox.citytrack.Domain.UseCases

import com.example.vendox.citytrack.Data.Repository.AuthRepository
import com.example.vendox.citytrack.Domain.DataClasses.Request.EmailRegistraion
import java.util.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import okhttp3.Response


class RegisterUseCase {
    private lateinit var mRepository: AuthRepository

    constructor(authRepository: AuthRepository) {
        mRepository = authRepository
    }

    fun registerEmail(emailRegistraion: EmailRegistraion) : Observable<ResponseBody>{
        return mRepository.registerEmail(emailRegistraion)
    }

}