package com.example.github_user_app.di

import android.content.Context
import com.example.github_user_app.data.Repository
import com.example.github_user_app.data.local.FavoriteUserDatabase
import com.example.github_user_app.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return Repository.getInstance(apiService, dao)
    }
}