package com.example.vendox.citytrack.Presentation.View.Main.Profile

import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity

import com.example.vendox.citytrack.R

class ProfileFragment : Fragment() {
    lateinit var recyclerViewCities: RecyclerView
    lateinit var citiesRecyclerViewAdapter: CitiesRecyclerViewAdapter
    private val dataCities= ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        dataCities.add("Москва")
        dataCities.add("Барселона")

        recyclerViewCities = view.findViewById(R.id.profile_rv_cities)
        recyclerViewCities.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        citiesRecyclerViewAdapter = CitiesRecyclerViewAdapter(dataCities, activity)
        recyclerViewCities.adapter = citiesRecyclerViewAdapter
        return view
    }

    override fun onResume() {
        (activity as MapBoxActivity).setActionBarTitle("Профиль")
        super.onResume()
    }



}
