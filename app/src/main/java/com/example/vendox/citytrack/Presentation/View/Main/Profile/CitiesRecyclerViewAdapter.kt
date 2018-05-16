package com.example.vendox.citytrack.Presentation.View.Main.Profile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Presentation.View.Main.Friends.FriendsToInviteRecyclerViewAdapter
import com.example.vendox.citytrack.R
import kotlinx.android.synthetic.main.profile_cities_rv_item.view.*


class CitiesRecyclerViewAdapter(val data:ArrayList<String>, val context:Context) : RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return CitiesRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_cities_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.userCity.text = data.get(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userCity = itemView.rv_cities_city

    }
}