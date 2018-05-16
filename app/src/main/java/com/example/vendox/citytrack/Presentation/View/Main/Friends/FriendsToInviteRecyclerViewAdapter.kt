package com.example.vendox.citytrack.Presentation.View.Main.Friends

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Domain.DataClasses.Response.InviteFriends
import com.example.vendox.citytrack.R
import kotlinx.android.synthetic.main.cardview_friends_to_invite.view.*

class FriendsToInviteRecyclerViewAdapter(val data:ArrayList<InviteFriends>, val context: Context) : RecyclerView.Adapter<FriendsToInviteRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.friendName.text = data.get(position).name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_friends_to_invite, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var friendName = itemView.friend_cardview_user_name
        var friendPhoto = itemView.friend_cardview_user_photo
        var friendNetworkName = itemView.friend_cardview_network_name
        var friendNetworkIcon = itemView.friend_cardview_network_icon
    }
}