package com.example.github_user_app.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.github_user_app.databinding.ActivityDetailUserBinding
import com.example.github_user_app.ui.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val githubUserDetailViewModel by viewModels<GithubUserDetailViewModel>()
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserData()

        setViewPager()
    }

    private fun setUserData() {
        username = intent.getStringExtra("username")!!

        githubUserDetailViewModel.getGithubUserDetail(username)

        githubUserDetailViewModel.githubUserDetail.observe(this) { userDetail ->
            Glide
                .with(this)
                .load(userDetail.avatarUrl)
                .fitCenter()
                .into(binding.ivProfile)

            binding.tvUsername.text = userDetail.login
            binding.tvName.text = userDetail.name
            binding.tvTotalFollowers.text = userDetail.followers.toString()
            binding.tvTotalFollowing.text = userDetail.following.toString()
        }
    }

    private fun setViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = ViewPagerAdapter(this).getPageTitle(position)
        }.attach()
    }
}