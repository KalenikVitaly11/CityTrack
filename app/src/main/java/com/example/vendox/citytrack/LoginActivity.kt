package com.example.vendox.citytrack

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.example.vendox.citytrack.EnterFragment.MainFragment
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.util.VKUtil


class LoginActivity : AppCompatActivity() {
    lateinit var backgroundView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        backgroundView = findViewById<ImageView>(R.id.login_background)
        val backgroundCity = BitmapFactory.decodeResource(this.resources, R.drawable.background_city)
        //Blurry.with(this)
        //        .radius(10)
        //        .async()
        //        .from(backgroundCity)
        //        .into(backgroundView)

        Handler().postDelayed({
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_up, R.animator.disappear)
                    .replace(R.id.fragment_container, MainFragment())
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
