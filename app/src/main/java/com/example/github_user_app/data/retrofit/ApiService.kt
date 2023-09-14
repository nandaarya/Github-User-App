package com.example.github_user_app.data.retrofit

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
}