package com.example.github_user_app.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.databinding.FragmentFollowBinding
import com.example.github_user_app.ui.home.GithubUserListAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: GithubUserListAdapter
    private lateinit var username: String
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowList.layoutManager = layoutManager

        if (isAdded && !isDetached) {
            val userFollowViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[UserFollowViewModel::class.java]

            userFollowViewModel.followList.observe(viewLifecycleOwner) { followList ->
                if (followList != null) {
                    adapter = GithubUserListAdapter(followList)
                    binding.rvFollowList.adapter = adapter
                }
            }

            if (position == 1){
                userFollowViewModel.getFollowList(username, "follower")
            } else {
                userFollowViewModel.getFollowList(username, "following")
            }
        }
    }

    companion object {
        var ARG_USERNAME: String = ""
        var ARG_POSITION = "section_number"
    }
}