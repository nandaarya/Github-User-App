package com.example.github_user_app.data.retrofit

import com.example.github_user_app.BuildConfig
import com.example.github_user_app.data.response.GithubUserDetailResponse
import com.example.github_user_app.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getGithubUser(
        @Query("q") username: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getGithubUserDetail(
        @Path("username") username: String
    ): Call<GithubUserDetailResponse>
}