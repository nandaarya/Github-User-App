package com.example.github_user_app.data.response

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)
