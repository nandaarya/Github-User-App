package com.example.github_user_app.ui.follow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github_user_app.data.response.FollowUserResponseItem
import com.example.github_user_app.databinding.ItemLayoutBinding
import com.example.github_user_app.ui.detail.DetailUserActivity

class FollowListAdapter (private val followUserList: List<FollowUserResponseItem>) :
    RecyclerView.Adapter<FollowListAdapter.FollowListViewHolder>() {

    inner class FollowListViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(followerData: FollowUserResponseItem) {
            binding.tvUsername.text = followerData.login

            Glide
                .with(itemView.context)
                .load(followerData.avatarUrl)
                .fitCenter()
                .into(binding.ivProfilePhoto)

            binding.itemLayout.setOnClickListener {
                Toast.makeText(itemView.context, followerData.login, Toast.LENGTH_SHORT).show()

                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra("username", followerData.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowListViewHolder {
        return FollowListViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowListViewHolder, position: Int) {
        val follower = followUserList[position]
        holder.bind(follower)
    }

    override fun getItemCount(): Int {
        return followUserList.size
    }
}