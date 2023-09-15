package com.example.github_user_app.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.response.GithubUserDetailResponse
import com.example.github_user_app.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserDetailViewModel: ViewModel() {

    private val _githubUserDetail = MutableLiveData<GithubUserDetailResponse>()
    val githubUserDetail: LiveData<GithubUserDetailResponse> = _githubUserDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getGithubUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUserDetail(username)
        client.enqueue(object : Callback<GithubUserDetailResponse> {
            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserDetail.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}