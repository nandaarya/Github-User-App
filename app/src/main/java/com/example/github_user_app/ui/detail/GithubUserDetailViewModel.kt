package com.example.github_user_app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.Result
import com.example.github_user_app.data.local.FavoriteUser
import com.example.github_user_app.data.response.DetailUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubUserDetailViewModel(private val repository: Repository) : ViewModel() {

    private val _githubUserDetail = MediatorLiveData<Result<DetailUserResponse>>()
    val githubUserDetail: LiveData<Result<DetailUserResponse>> = _githubUserDetail

    private val _isFavorite = MediatorLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getGithubUserDetail(username: String) {
        val liveData = repository.getDetailUser(username)
        _githubUserDetail.addSource(liveData) { result ->
            _githubUserDetail.value = result
        }
    }

    fun isFavorite(username: String) {
        val liveData = repository.isFavorite(username)
        _isFavorite.addSource(liveData) { result ->
            _isFavorite.value = result
        }
    }

    fun saveFavorite(favorite: FavoriteUser) =
        viewModelScope.launch(Dispatchers.IO) { repository.saveFavorite(favorite) }

    fun deleteFavorite(favorite: FavoriteUser) =
        viewModelScope.launch(Dispatchers.IO) { repository.delete(favorite) }
}