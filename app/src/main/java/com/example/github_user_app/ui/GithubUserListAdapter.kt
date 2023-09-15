package com.example.github_user_app.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_user_app.data.response.ItemsItem
import com.example.github_user_app.databinding.ItemLayoutBinding

class GithubUserListAdapter(private val githubUserList: List<ItemsItem>) :
    RecyclerView.Adapter<GithubUserListAdapter.GithubUserViewHolder>() {

    inner class GithubUserViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: ItemsItem) {
            binding.tvUsername.text = githubUser.login

            Glide
                .with(itemView.context)
                .load(githubUser.avatarUrl)
                .fitCenter()
                .into(binding.ivProfilePhoto)

            binding.itemLayout.setOnClickListener {
                Toast.makeText(itemView.context, githubUser.login, Toast.LENGTH_SHORT).show()

                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra("username", githubUser.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return githubUserList.size
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val githubUser = githubUserList[position]
        holder.bind(githubUser)
    }
}