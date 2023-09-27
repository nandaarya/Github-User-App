package com.example.github_user_app.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.ViewModelFactory
import com.example.github_user_app.databinding.ActivityFavoriteBinding
import com.example.github_user_app.ui.home.GithubUserListAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: GithubUserListAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        favoriteUserViewModel = ViewModelProvider(this, factory)[FavoriteUserViewModel::class.java]

        favoriteUserViewModel.favoriteUserList.observe(this) {
            showLoading(false)
            if (it != null) {
                val layoutManager = LinearLayoutManager(this)
                binding.rvFavoriteUserList.layoutManager = layoutManager
                adapter = GithubUserListAdapter(it)
                binding.rvFavoriteUserList.adapter = adapter
            } else {
                binding.rvFavoriteUserList.visibility = View.GONE
                binding.tvFavoriteNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFavoriteUserList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}