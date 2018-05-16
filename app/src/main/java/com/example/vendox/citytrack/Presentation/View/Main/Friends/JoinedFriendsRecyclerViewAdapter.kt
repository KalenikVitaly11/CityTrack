package com.example.vendox.citytrack.Presentation.View.Main.Friends

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Domain.DataClasses.Response.JoinedFriends
import com.example.vendox.citytrack.R
import kotlinx.android.synthetic.main.cardview_friends_in_citytrack.view.*
import kotlinx.android.synthetic.main.cardview_friends_to_invite.view.*

class JoinedFriendsRecyclerViewAdapter(val data:ArrayList<JoinedFriends>, val context:Context) : RecyclerView.Adapter<JoinedFriendsRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return JoinedFriendsRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_friends_in_citytrack, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.friendName.text = data.get(position).name
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var friendName = itemView.joined_friend_cardview_name
        var friendPhoto = itemView.joined_friend_cardview_image
        var friendCity = itemView.joined_friend_cardview_city
    }
}