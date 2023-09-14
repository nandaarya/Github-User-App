package com.example.github_user_app.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()
    private lateinit var adapter: GithubUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithubList.layoutManager = layoutManager

        adapter = GithubUserListAdapter(emptyList())
        binding.rvUserGithubList.adapter = adapter

        githubUserViewModel.githubUserList.observe(this) { githubUsers ->
            adapter = GithubUserListAdapter(githubUsers)
            binding.rvUserGithubList.adapter = adapter
        }
    }
}