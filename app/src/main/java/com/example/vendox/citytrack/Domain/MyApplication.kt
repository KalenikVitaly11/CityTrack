package com.example.vendox.citytrack.Domain

import android.app.Application
import com.facebook.FacebookSdk
import com.vk.sdk.VKSdk

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(applicationContext)
    }
}