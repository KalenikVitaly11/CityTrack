package com.example.vendox.citytrack.Presentation.View.Main.Profile

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity

import com.example.vendox.citytrack.R

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onResume() {
        (activity as MapBoxActivity).setActionBarTitle("Профиль")
        super.onResume()
    }

}
