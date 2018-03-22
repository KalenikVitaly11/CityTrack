package com.example.vendox.citytrack

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.example.vendox.citytrack.R.string.login
import kotlinx.android.synthetic.main.activity_map_box.*

class MapBoxActivity : AppCompatActivity() {

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

        fragmentManager.beginTransaction().replace(R.id.rootmap, MapBoxFragment()).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun goToMapBox(){

        fragmentManager
                .beginTransaction()
                .replace(R.id.rootmap, MapBoxFragment())
                .addToBackStack(null)
                .commit()

    }

    fun bottomNavigationInit(navigation: BottomNavigationView){

    }
}



