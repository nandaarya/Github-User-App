package com.example.github_user_app.ui.home

import android.os.Bundle
import android.view.View
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

        setRecyclerViewData()

        githubUserViewModel.githubUserList.observe(this) { githubUsers ->
            if (githubUsers != null) {
                adapter = GithubUserListAdapter(githubUsers)
                binding.rvUserGithubList.adapter = adapter
            }
        }

        githubUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setRecyclerViewData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithubList.layoutManager = layoutManager

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    githubUserViewModel.setUsername(searchBar.text.toString())
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvUserGithubList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}