package com.example.github_user_app.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github_user_app.ui.follow.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(username, position + 1)
    }

    override fun getItemCount(): Int {
        return 2
    }
}