package com.example.vendox.citytrack.Domain.DataClasses.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailLoginResponse(@SerializedName("id") @Expose val id:Int,
                              @SerializedName("name") @Expose val name:String,
                              @SerializedName("email") @Expose val email:String,
                              @SerializedName("token") @Expose val token:String)