package com.example.github_user_app.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_user_app.data.response.DetailUserResponse
import com.example.github_user_app.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserDetailViewModel: ViewModel() {

    private val _githubUserDetail = MutableLiveData<DetailUserResponse>()
    val githubUserDetail: LiveData<DetailUserResponse> = _githubUserDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getGithubUserDetail(username: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val client = ApiConfig.getApiService().getGithubUserDetail(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _githubUserDetail.value = response.body()
                    } else {
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }
}