package com.example.github_user_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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

    //
//    fun followerUser(githubFollower: String): LiveData<Resource<List<FollowModel>>> = liveData {
//        emit(Resource.Loading)
//        try {
//            val response = apiService.getFollowersUsers(githubFollower)
//            emit(Resource.Success(response))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message.toString()))
//        }
//    }
//
//    fun followingUser(githubFollowing: String): LiveData<Resource<List<FollowModel>>> = liveData {
//        emit(Resource.Loading)
//        try {
//            val response = apiService.getFollowingUsers(githubFollowing)
//            emit(Resource.Success(response))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.message.toString()))
//        }
//    }
//
//    suspend fun insert(favorite: UserModel) {
//        mFavDao.insert(favorite)
//    }
//
//    fun getAllChanges(): LiveData<List<UserModel>> = mFavDao.getAllChanges()
//
//    fun searchFav(name: String): LiveData<List<UserModel>> = mFavDao.searchFav(name)
//
//    fun isFavorite(name: String): Boolean = mFavDao.isFavorite(name)
//
//    suspend fun delete(favorite: UserModel) {
//        mFavDao.delete(favorite)
//    }
//
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