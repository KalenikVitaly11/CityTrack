package com.example.vendox.citytrack.Presentation.View.Main.Friends

import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vendox.citytrack.Domain.DataClasses.Response.InviteFriends
import com.example.vendox.citytrack.Domain.DataClasses.Response.JoinedFriends
import com.example.vendox.citytrack.Presentation.View.Main.MapBoxActivity

import com.example.vendox.citytrack.R

class FriendsFragment : Fragment() {
    lateinit var recyclerViewFriendsToInvite: RecyclerView
    lateinit var friendsInviteRecyclerViewAdapter: FriendsToInviteRecyclerViewAdapter
    private val dataFriends= ArrayList<InviteFriends>()

    lateinit var recyclerViewJoinedFriends:RecyclerView
    lateinit var joinedFriendsRecyclerViewAdapter: JoinedFriendsRecyclerViewAdapter
    private val dataJoinedFriends = ArrayList<JoinedFriends>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        val friend = InviteFriends("Ринат Зубайдуллин", "", "Ваш друг с Facebook")
        dataFriends.add(friend)
        dataFriends.add(friend)
        dataFriends.add(friend)
        dataFriends.add(friend)
        dataFriends.add(friend)
        recyclerViewFriendsToInvite = view.findViewById(R.id.recycler_view_friends_to_invite)
        recyclerViewFriendsToInvite.layoutManager = LinearLayoutManager(activity)
        recyclerViewFriendsToInvite.setNestedScrollingEnabled(false)
        friendsInviteRecyclerViewAdapter = FriendsToInviteRecyclerViewAdapter(dataFriends, activity)
        recyclerViewFriendsToInvite.adapter = friendsInviteRecyclerViewAdapter

        val joinedFriend = JoinedFriends("Виталий Каленик", "Москва", "")
        dataJoinedFriends.add(joinedFriend)
        dataJoinedFriends.add(joinedFriend)
        dataJoinedFriends.add(joinedFriend)
        dataJoinedFriends.add(joinedFriend)
        dataJoinedFriends.add(joinedFriend)
        recyclerViewJoinedFriends = view.findViewById(R.id.recycler_view_joined_friends)
        recyclerViewJoinedFriends.layoutManager = LinearLayoutManager(activity)
        recyclerViewJoinedFriends.setNestedScrollingEnabled(false)
        joinedFriendsRecyclerViewAdapter = JoinedFriendsRecyclerViewAdapter(dataJoinedFriends, activity)
        recyclerViewJoinedFriends.adapter = joinedFriendsRecyclerViewAdapter


        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MapBoxActivity).setActionBarTitle("Друзья")
    }

}
