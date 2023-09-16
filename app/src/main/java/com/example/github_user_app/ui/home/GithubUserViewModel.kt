package com.example.github_user_app.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_user_app.data.response.GithubUserResponse
import com.example.github_user_app.data.response.ItemsItem
import com.example.github_user_app.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserViewModel : ViewModel() {

    private val _githubUserList = MutableLiveData<List<ItemsItem>>()
    val githubUserList: LiveData<List<ItemsItem>> = _githubUserList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUsername(username: String) {
        findGithubUser(username)
    }

    init {
        findGithubUser(USERNAME)
    }

    private fun findGithubUser(username: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val client = ApiConfig.getApiService().getGithubUser(username)
            client.enqueue(object : Callback<GithubUserResponse> {
                override fun onResponse(
                    call: Call<GithubUserResponse>,
                    response: Response<GithubUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _githubUserList.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object{
        private const val USERNAME = "nandaarya"
    }
}