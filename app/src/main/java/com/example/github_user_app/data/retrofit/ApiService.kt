package com.example.github_user_app.data.retrofit

import com.example.github_user_app.data.response.GithubUserDetailResponse
import com.example.github_user_app.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getGithubUser(
        @Query("q") username: String
    ): Call<GithubUserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getGithubUserDetail(
        @Path("username") username: String
    ): Call<GithubUserDetailResponse>
}