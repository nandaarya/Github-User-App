package com.example.github_user_app.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.Result
import com.example.github_user_app.data.response.ItemsItem

class UserFollowViewModel(private val repository: Repository) : ViewModel() {
    private val _followerList = MediatorLiveData<Result<List<ItemsItem>>>()
    val followerList: LiveData<Result<List<ItemsItem>>> = _followerList

    private val _followingList = MediatorLiveData<Result<List<ItemsItem>>>()
    val followingList: LiveData<Result<List<ItemsItem>>> = _followingList

    fun getFollowerList(username: String) {
        val liveData = repository.getFollowerUser(username)
        _followerList.addSource(liveData) { result ->
            _followerList.value = result
        }
    }

    fun getFollowingList(username: String) {
        val liveData = repository.getFollowingUser(username)
        _followingList.addSource(liveData) { result ->
            _followingList.value = result
        }
    }
}