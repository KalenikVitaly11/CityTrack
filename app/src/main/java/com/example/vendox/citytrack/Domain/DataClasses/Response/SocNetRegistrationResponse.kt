package com.example.vendox.citytrack.Domain.DataClasses.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Vitaly on 02.04.2018.
 */
data class SocNetRegistrationResponse(@SerializedName("id") @Expose val id: String,
                                      @SerializedName("name") @Expose val name: String,
                                      @SerializedName("surname") @Expose val surname: String,
                                      @SerializedName("email") @Expose val email: String,
                                      @SerializedName("vk_id") @Expose val vkId: String,
                                      @SerializedName("facebook_id") @Expose val fbId: String,
                                      @SerializedName("token") @Expose val token: String)