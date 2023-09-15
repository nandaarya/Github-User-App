package com.example.github_user_app.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github_user_app.ui.follow.FollowerFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, username: String) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = arrayListOf(FollowerFragment(username), FollowingFragment())
    private val titleList = arrayListOf("Follower", "Following")

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}