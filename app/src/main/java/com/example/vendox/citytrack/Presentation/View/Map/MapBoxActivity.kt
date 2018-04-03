package com.example.vendox.citytrack.Presentation.View.Map

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.vendox.citytrack.R
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_map_box.*

class MapBoxActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationViewEx

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                goToMapBox()
                return@OnNavigationItemSelectedListener true
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

//        fragmentManager.beginTransaction().replace(R.id.rootmap, MapBoxFragment()).commit()

    }

    fun initBottomNavigation(){
        bottomNavigationView.enableShiftingMode(false)
        bottomNavigationView.enableAnimation(false)
        bottomNavigationView.enableItemShiftingMode(false)
        bottomNavigationView.setTextVisibility(false)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun goToMapBox() {

        fragmentManager
                .beginTransaction()
                .replace(R.id.rootmap, MapBoxFragment())
                .addToBackStack(null)
                .commit()

    }
}