package com.example.github_user_app.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.github_user_app.R
import com.example.github_user_app.databinding.ActivityDetailUserBinding
import com.example.github_user_app.ui.follow.SectionsPagerAdapter
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

        githubUserDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

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
        val adapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabText = this.getString(TAB_TITLES[position])
            tab.text = tabText
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAB_TITLES = listOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}