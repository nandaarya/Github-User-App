package com.example.github_user_app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.github_user_app.R
import com.example.github_user_app.util.ViewModelFactory
import com.example.github_user_app.data.Result
import com.example.github_user_app.data.local.FavoriteUser
import com.example.github_user_app.data.response.DetailUserResponse
import com.example.github_user_app.databinding.ActivityDetailUserBinding
import com.example.github_user_app.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: String
    private lateinit var githubUserDetailViewModel: GithubUserDetailViewModel
    private lateinit var detailUser: DetailUserResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        githubUserDetailViewModel =
            ViewModelProvider(this, factory)[GithubUserDetailViewModel::class.java]

        username = intent.getStringExtra("username") ?: "DefaultUsername"

        githubUserDetailViewModel.getGithubUserDetail(username)

        githubUserDetailViewModel.githubUserDetail.observe(this) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    showLoading(false)
                    setUserData(it.data)
                    detailUser = it.data
                }
            }
        }

        setShare()

        setFavorite()

        setViewPager()
    }

    private fun setShare() {
        binding.btnShare.setOnClickListener{
            val username = detailUser.login
            val name = detailUser.name
            val follower = detailUser.followers
            val following = detailUser.following
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Detail User Github \n" +
                        "Username: $username \n" +
                        "Nama: $name \n" +
                        "Jumlah Follower: $follower \n" +
                        "Jumlah Following: $following" )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setFavorite() {
        val ivFavorite = binding.ivFavorite
        githubUserDetailViewModel.isFavorite(username)
        githubUserDetailViewModel.isFavorite.observe(this) {
            when (it) {
                true -> {
                    ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            ivFavorite.context,
                            R.drawable.baseline_favorite_24
                        )
                    )
                    ivFavorite.setOnClickListener {
                        val user = FavoriteUser(detailUser.login, detailUser.avatarUrl)
                        githubUserDetailViewModel.deleteFavorite(user)
                    }
                }
                false -> {
                    ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            ivFavorite.context,
                            R.drawable.baseline_favorite_border_24
                        )
                    )
                    ivFavorite.setOnClickListener {
                        val user = FavoriteUser(detailUser.login, detailUser.avatarUrl)
                        githubUserDetailViewModel.saveFavorite(user)
                    }
                }
            }
        }
    }

    private fun setUserData(userDetail: DetailUserResponse) {
        Glide
            .with(this)
            .load(userDetail.avatarUrl)
            .fitCenter()
            .into(binding.ivProfile)

        binding.tvUsername.text = userDetail.login
        binding.tvName.text = userDetail.name

        binding.tvTotalFollowers.text =
            getString(R.string.followers_template, userDetail.followers.toString())
        binding.tvTotalFollowing.text =
            getString(R.string.following_template, userDetail.following.toString())
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
        with(binding) {
            tvUsername.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvName.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvTotalFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvTotalFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private val TAB_TITLES = listOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}