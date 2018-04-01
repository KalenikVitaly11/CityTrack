package com.example.vendox.citytrack.Presentation.View.Authorization

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import com.example.vendox.citytrack.Data.AuthApi
import com.example.vendox.citytrack.Presentation.View.Authorization.Welcome.WelcomeFragment
import com.example.vendox.citytrack.R
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError


class LoginActivity : AppCompatActivity() {
    lateinit var backgroundView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        backgroundView = findViewById<ImageView>(R.id.login_background)
        val backgroundCity = BitmapFactory.decodeResource(this.resources, R.drawable.background_city)

        Handler().postDelayed({
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_up, R.animator.disappear)
                    .replace(R.id.fragment_container, WelcomeFragment())
                    .commit()
            //VKSdk.login(this, *arrayOf())
        }, 500)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken?) {
                Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: VKError?) {
                Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
            }

        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
