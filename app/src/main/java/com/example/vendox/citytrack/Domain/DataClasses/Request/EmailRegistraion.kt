package com.example.vendox.citytrack.Domain.DataClasses.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class EmailRegistraion(@SerializedName("name") @Expose val name: String,
                            @SerializedName("surname") @Expose val surname: String,
                            @SerializedName("email") @Expose val email: String,
                            @SerializedName("password") @Expose val password: String)