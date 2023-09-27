package com.example.github_user_app.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.util.ViewModelFactory
import com.example.github_user_app.data.Result
import com.example.github_user_app.databinding.FragmentFollowBinding
import com.example.github_user_app.ui.adapter.GithubUserListAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: GithubUserListAdapter
    private lateinit var userFollowViewModel: UserFollowViewModel
    private lateinit var username: String
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "DefaultUsername"
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowList.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        userFollowViewModel = ViewModelProvider(this, factory)[UserFollowViewModel::class.java]

        userFollowViewModel.followerList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter = GithubUserListAdapter(it.data)
                    binding.rvFollowList.adapter = adapter
                }
            }
        }

        userFollowViewModel.followingList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter = GithubUserListAdapter(it.data)
                    binding.rvFollowList.adapter = adapter
                }
            }
        }

        if (isAdded && !isDetached) {
            if (position == 1){
                userFollowViewModel.getFollowerList(username)
            } else {
                userFollowViewModel.getFollowingList(username)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFollowList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        var ARG_USERNAME: String = ""
        var ARG_POSITION = "section_number"
    }
}
