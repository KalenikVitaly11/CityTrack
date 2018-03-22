package com.example.vendox.citytrack.Retrofit.Request

import com.google.gson.annotations.SerializedName

/**
 * Created by vendox on 27.02.18.
 */
data class LoginRequest(
        @SerializedName("email") val login: String,
        @SerializedName("password") val password: String
)