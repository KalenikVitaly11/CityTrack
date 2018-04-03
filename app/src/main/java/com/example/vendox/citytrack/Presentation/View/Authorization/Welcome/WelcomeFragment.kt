package com.example.vendox.citytrack.Presentation.View.Authorization.Welcome

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.vendox.citytrack.Presentation.View.Authorization.Login.LoginFragment
import com.example.vendox.citytrack.Presentation.View.Authorization.Registration.FirstScreen.RegistrationFragment
import com.example.vendox.citytrack.R


class WelcomeFragment: Fragment(){
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    companion object {
        fun newInstance(): WelcomeFragment {
            return WelcomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.main_fragment, container, false)

        btnLogin = rootView!!.findViewById<Button>(R.id.go_to_login_button)
        btnLogin.setOnClickListener(View.OnClickListener {
            goToLogin()
        })

        btnRegister = rootView!!.findViewById<Button>(R.id.go_to_registration_button)
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