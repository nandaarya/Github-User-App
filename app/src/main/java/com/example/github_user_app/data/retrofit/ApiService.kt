package com.example.github_user_app.data.retrofit

import com.example.github_user_app.data.response.DetailUserResponse
import com.example.github_user_app.data.response.GithubUserResponse
import com.example.github_user_app.data.response.ItemsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getGithubUser(
        @Query("q") username: String
    ): GithubUserResponse

    @GET("users/{username}")
    suspend fun getGithubUserDetail(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowerList(
        @Path("username") username: String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getFollowingList(
        @Path("username") username: String
    ): List<ItemsItem>
}