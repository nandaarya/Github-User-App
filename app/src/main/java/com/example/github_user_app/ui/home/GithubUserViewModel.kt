package com.example.github_user_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.response.ItemsItem
import com.example.github_user_app.data.Result

class GithubUserViewModel(private val repository: Repository) : ViewModel() {
    fun setUsername(username: String): LiveData<Result<List<ItemsItem>>> = repository.findGithubUser(username)

}