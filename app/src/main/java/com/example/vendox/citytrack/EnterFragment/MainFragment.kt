package com.example.vendox.citytrack.EnterFragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.vendox.citytrack.R


class MainFragment: Fragment(){
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.main_fragment, container, false)

        btnLogin = rootView!!.findViewById<Button>(R.id.enter_button)
        btnLogin.setOnClickListener(View.OnClickListener {
            goToLogin()
        })

        btnRegister = rootView!!.findViewById<Button>(R.id.registration_button)
        btnRegister.setOnClickListener(View.OnClickListener {
            goToRegister()
        })
        return rootView
    }

    fun goToRegister(){
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_up, R.animator.disappear, R.animator.appear, R.animator.slide_out_bottom)
                .replace(R.id.fragment_container, RegistrationFragment())
                .addToBackStack(null)
                .commit()
    }

    fun goToLogin(){
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_up, R.animator.disappear, R.animator.appear, R.animator.slide_out_bottom)
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
    }
}