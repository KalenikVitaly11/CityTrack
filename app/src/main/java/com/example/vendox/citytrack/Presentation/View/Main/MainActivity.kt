package com.example.vendox.citytrack.Presentation.View.Main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.example.vendox.citytrack.Presentation.View.Main.Profile.ProfileFragment
import com.example.vendox.citytrack.R
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationViewEx
    lateinit var actionBarTitle:TextView

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

            R.id.navigation_profile -> {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.rootmap, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_box)
        bottomNavigationView = findViewById(R.id.navigation)
        setMainCustomActionBar()
        initBottomNavigation()

        fragmentManager.beginTransaction().replace(R.id.rootmap, MapBoxFragment()).commit()

    }

    fun setMainCustomActionBar() {
        val abar = supportActionBar
        val viewActionBar = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val params = ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER)
        actionBarTitle = viewActionBar.findViewById(R.id.action_bar_title)
        abar!!.setCustomView(viewActionBar, params)
        abar.setDisplayShowCustomEnabled(true)
        abar.setDisplayShowTitleEnabled(false)
    }

    fun initBottomNavigation(){
        bottomNavigationView.enableShiftingMode(false)
        bottomNavigationView.enableAnimation(false)
        bottomNavigationView.enableItemShiftingMode(false)
        bottomNavigationView.setTextVisibility(false)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setActionBarTitle(title: String) {
        actionBarTitle.text = title
    }

    fun goToMapBox() {

        fragmentManager
                .beginTransaction()
                .replace(R.id.rootmap, MapBoxFragment())
                .addToBackStack(null)
                .commit()

    }
}