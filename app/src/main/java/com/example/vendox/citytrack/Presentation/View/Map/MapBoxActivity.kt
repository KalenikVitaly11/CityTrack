package com.example.vendox.citytrack.Presentation.View.Map

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.vendox.citytrack.R
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_map_box.*
import kotlinx.android.synthetic.main.map_box_fragment.*

class MapBoxActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationViewEx

    private var back_pressed: Long = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (item.isEnabled) {
                } else {
                    goToMapBox()
                    return@OnNavigationItemSelectedListener true
                }
            }
//            R.id.navigation_dashboard -> {
//                message.setText(R.string.title_dashboard)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_notifications -> {
//                message.setText(R.string.title_notifications)
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_box)
        bottomNavigationView = findViewById(R.id.navigation)
        initBottomNavigation()

        fragmentManager.beginTransaction().replace(R.id.rootmap, MapFragment()).commit()

    }

    fun initBottomNavigation(){
        bottomNavigationView.enableShiftingMode(false)
        bottomNavigationView.enableAnimation(false)
        bottomNavigationView.enableItemShiftingMode(false)
        bottomNavigationView.setTextVisibility(false)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finish()
        }
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show()
        back_pressed = System.currentTimeMillis()
    }


    fun goToMapBox() {

        fragmentManager
                .beginTransaction()
                .replace(R.id.rootmap, MapFragment())
                .addToBackStack(null)
                .commit()

    }
}