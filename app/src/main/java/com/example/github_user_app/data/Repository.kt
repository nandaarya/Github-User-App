package com.example.github_user_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.github_user_app.data.local.FavoriteUser
import com.example.github_user_app.data.local.FavoriteUserDAO
import com.example.github_user_app.data.response.DetailUserResponse
import com.example.github_user_app.data.response.ItemsItem
import com.example.github_user_app.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers

class Repository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDAO,
) {
    fun findGithubUser(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getGithubUser(username)
                emit(Result.Success(response.items))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getGithubUserDetail(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }


    fun getFollowerUser(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowerList(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getFollowingUser(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowingList(username)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    suspend fun saveFavorite(favorite: FavoriteUser) {
        favoriteUserDao.insert(favorite)
    }

    suspend fun delete(favorite: FavoriteUser) {
        favoriteUserDao.delete(favorite)
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = favoriteUserDao.getAllFavoriteUser()

    fun isFavorite(username: String): LiveData<Boolean> =
        favoriteUserDao.isFavorite(username)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDAO,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, favoriteUserDao)
        }.also { instance = it }
    }
}