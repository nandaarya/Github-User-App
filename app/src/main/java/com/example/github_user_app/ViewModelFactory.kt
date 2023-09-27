package com.example.github_user_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_user_app.data.Repository
import com.example.github_user_app.di.Injection
import com.example.github_user_app.ui.detail.GithubUserDetailViewModel
import com.example.github_user_app.ui.follow.UserFollowViewModel
import com.example.github_user_app.ui.home.GithubUserViewModel

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(GithubUserViewModel::class.java) -> return GithubUserViewModel(repository) as T
            modelClass.isAssignableFrom(GithubUserDetailViewModel::class.java) -> return GithubUserDetailViewModel(repository) as T
            modelClass.isAssignableFrom(UserFollowViewModel::class.java) -> return UserFollowViewModel(repository) as T
//            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> return FavoriteViewModel(repository) as T
//            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}