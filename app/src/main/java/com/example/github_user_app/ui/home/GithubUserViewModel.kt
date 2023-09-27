package com.example.github_user_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.Result
import com.example.github_user_app.data.response.ItemsItem

class GithubUserViewModel(private val repository: Repository) : ViewModel() {
    private val _githubUserList = MediatorLiveData<Result<List<ItemsItem>>>()
    val githubUserList: LiveData<Result<List<ItemsItem>>> = _githubUserList

    fun findGithubUser(username: String) {
        val liveData = repository.findGithubUser(username)
        _githubUserList.addSource(liveData) { result ->
            _githubUserList.value = result
        }
    }

    init {
        findGithubUser("nandaarya")
    }
}