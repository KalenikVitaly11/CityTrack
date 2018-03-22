package com.example.vendox.citytrack.Retrofit.Request

import com.google.gson.annotations.SerializedName

/**
 * Created by vendox on 28.02.18.
 */
data class RegisterRequest(
        @SerializedName("name") val name: String,
        @SerializedName("surname") val surname: String,
        @SerializedName("email") val login: String,
        @SerializedName("password") val password: String
)