package com.example.github_user_app.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.databinding.FragmentFollowingBinding

class FollowingFragment(private val username: String) : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowingList.layoutManager = layoutManager

        adapter = FollowListAdapter(emptyList())
        binding.rvFollowingList.adapter = adapter

        if (isAdded && !isDetached) {
            val userFollowingViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[UserFollowingViewModel::class.java]

            userFollowingViewModel.getFollowingList(username)

            userFollowingViewModel.followingList.observe(viewLifecycleOwner) { followingList ->
                if (followingList != null) {
                    adapter = FollowListAdapter(followingList)
                    binding.rvFollowingList.adapter = adapter
                }
            }
        }
    }
}