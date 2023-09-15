package com.example.github_user_app.ui.follow

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_user_app.data.response.FollowUserResponseItem
import com.example.github_user_app.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowingViewModel: ViewModel() {

    private val _followingList = MutableLiveData<List<FollowUserResponseItem>>()
    val followingList: LiveData<List<FollowUserResponseItem>> = _followingList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowingList (username: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val client = ApiConfig.getApiService().getFollowingList(username)
            client.enqueue(object : Callback<List<FollowUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<FollowUserResponseItem>>,
                    response: Response<List<FollowUserResponseItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followingList.value = response.body()
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
}