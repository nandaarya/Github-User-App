package com.example.github_user_app.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

//    private val fragmentList = arrayListOf(FollowerFragment(username), FollowingFragment(username))
//    private val titleList = arrayListOf("Follower", "Following")

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val username = fragmentList[position].arguments?.getString("username", "")
        val fragment = fragmentList[position]
        fragment.arguments = Bundle().apply {
            putString("username", username)
        }
        return fragment
    }

    fun getPageTitle(position: Int): CharSequence {
        return fragmentList[position].arguments?.getString("title", "") ?: ""
    }
}