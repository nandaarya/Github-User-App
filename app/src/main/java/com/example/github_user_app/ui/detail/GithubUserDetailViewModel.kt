package com.example.github_user_app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.Result
import com.example.github_user_app.data.response.DetailUserResponse

class GithubUserDetailViewModel(private val repository: Repository) : ViewModel() {

    private val _githubUserDetail = MediatorLiveData<Result<DetailUserResponse>>()
    val githubUserDetail: LiveData<Result<DetailUserResponse>> = _githubUserDetail

    fun getGithubUserDetail(username: String) {
        val liveData = repository.getDetailUser(username)
        _githubUserDetail.addSource(liveData) { result ->
            _githubUserDetail.value = result
        }
    }
}