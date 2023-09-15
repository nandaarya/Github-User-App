package com.example.github_user_app.ui.follow

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.data.response.FollowUserResponseItem
import com.example.github_user_app.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowerViewModel: ViewModel() {

    private val _followerList = MutableLiveData<List<FollowUserResponseItem>>()
    val followerList: LiveData<List<FollowUserResponseItem>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowerList (username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowerList(username)
        client.enqueue(object : Callback<List<FollowUserResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowUserResponseItem>>,
                response: Response<List<FollowUserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerList.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}