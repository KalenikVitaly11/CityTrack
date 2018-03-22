package com.example.vendox.citytrack.EnterFragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import com.example.vendox.citytrack.R


class RegistrationFinishFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return inflater.inflate(R.layout.fragment_registration_finish, container, false)
    }
    companion object {

        fun newInstance(param1: String, param2: String): RegistrationFinishFragment {
            return RegistrationFinishFragment()
        }
    }
}// Required empty public constructor
