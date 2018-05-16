package com.example.vendox.citytrack.Domain.DataClasses.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForgotPasswordObject(@SerializedName("email") @Expose val email: String,
                                @SerializedName("code") @Expose val code: String)