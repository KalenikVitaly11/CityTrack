package com.example.vendox.citytrack.Retrofit.Response

import com.google.gson.annotations.SerializedName

/**
 * Created by vendox on 28.02.18.
 */
data class RegisterResponse (
        @SerializedName("status")val status: String? = null,
        @SerializedName("result")val result: String? = null
        )