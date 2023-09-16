package com.example.github_user_app.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_user_app.databinding.FragmentFollowerBinding
import com.example.github_user_app.ui.home.GithubUserListAdapter

//class FollowerFragment : Fragment() {
//
//    private lateinit var binding: FragmentFollowerBinding
//    private lateinit var adapter: GithubUserListAdapter
//    private lateinit var username: String
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentFollowerBinding.inflate(inflater, container, false)
//        // Inflate the layout for this fragment
//        username = arguments?.getString("username", "")!!
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val layoutManager = LinearLayoutManager(requireContext())
//        binding.rvFollowerList.layoutManager = layoutManager
//
//        if (isAdded && !isDetached) {
//            val userFollowerViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.NewInstanceFactory()
//            )[UserFollowerViewModel::class.java]
//
//            userFollowerViewModel.getFollowerList(username)
//
//            userFollowerViewModel.followerList.observe(viewLifecycleOwner) { followerList ->
//                if (followerList != null) {
//                    adapter = GithubUserListAdapter(followerList)
//                    binding.rvFollowerList.adapter = adapter
//                }
//            }
//        }
//    }
//}