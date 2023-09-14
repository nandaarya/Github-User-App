package com.example.github_user_app.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.response.GithubUserResponse
import com.example.github_user_app.data.response.ItemsItem
import com.example.github_user_app.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserViewModel : ViewModel() {

    private val _githubUserList = MutableLiveData<List<ItemsItem>>()
    val githubUserList: LiveData<List<ItemsItem>> = _githubUserList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        //        private const val TAG = "MainViewModel"
//        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
        private const val USERNAME = "nanda"
    }

    init {
        findGithubUser()
    }

    private fun findGithubUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(USERNAME)
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