package com.example.vendox.citytrack.Domain.DataClasses.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailLogin(@SerializedName("email") @Expose val email: String,
                      @SerializedName("password") @Expose val password: String)