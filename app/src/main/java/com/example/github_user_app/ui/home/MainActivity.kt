package com.example.github_user_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.R
import com.example.github_user_app.data.Result
import com.example.github_user_app.databinding.ActivityMainBinding
import com.example.github_user_app.ui.adapter.GithubUserListAdapter
import com.example.github_user_app.ui.favorite.FavoriteActivity
import com.example.github_user_app.ui.setting.SettingActivity
import com.example.github_user_app.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GithubUserListAdapter
    private lateinit var githubUserViewModel: GithubUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        githubUserViewModel = ViewModelProvider(this, factory)[GithubUserViewModel::class.java]

        setRecyclerViewData()

        setOptionMenu()

        githubUserViewModel.githubUserList.observe(this) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter = GithubUserListAdapter(it.data)
                    binding.rvUserGithubList.adapter = adapter
                }
            }
        }
    }

    private fun setOptionMenu() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_option -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
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
                    githubUserViewModel.findGithubUser(searchBar.text.toString())
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvUserGithubList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}