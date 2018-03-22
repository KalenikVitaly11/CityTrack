package com.example.vendox.citytrack

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    lateinit var backgroundView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        //backgroundView = findViewById<ImageView>(R.id.splash_screen_background)
        //val backgroundCity = BitmapFactory.decodeResource(this.resources, R.drawable.background_city)
        //Blurry.with(this)
        //        .radius(30)
        //        .async()
        //        .from(backgroundCity)
        //        .into(backgroundView)
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
