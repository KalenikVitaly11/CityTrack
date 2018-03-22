package com.example.vendox.citytrack.Retrofit.Response

import com.google.gson.annotations.SerializedName

/**
 * Created by vendox on 27.02.18.
 */
data class UserResponse (
        @SerializedName("status")val status: String? = null,
        @SerializedName("id")val id: Int = 0,
        @SerializedName("name")val name: String? = null,
        @SerializedName("email")val email: String? = null,
        @SerializedName("token")val token: String? = null)