package com.example.github_user_app.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.response.ItemsItem

class FavoriteUserViewModel(private val repository: Repository) : ViewModel() {
    private val _favoriteUserList = MediatorLiveData<List<ItemsItem>>()
    val favoriteUserList: LiveData<List<ItemsItem>> = _favoriteUserList

    private fun getFavoriteUser() {
        val liveData = repository.getFavoriteUser()
        _favoriteUserList.addSource(liveData) { favoriteUserList ->
            val convertedList = favoriteUserList.map { favoriteUser ->
                ItemsItem(favoriteUser.username, favoriteUser.avatarUrl ?: "")
            }
            _favoriteUserList.value = convertedList
        }
    }

    init {
        getFavoriteUser()
    }
}