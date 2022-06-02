package com.example.githubuser3.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser3.adapter.ListUserAdapter
import com.example.githubuser3.R
import com.example.githubuser3.databinding.FragmentFollowBinding
import com.example.githubuser3.datasource.User

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        showLoadingBar(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, { it ->
            if (it != null) {
                adapter.setList(it)
                showLoadingBar(false)
                adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        Intent(context, DetailUser::class.java).also {
                            it.putExtra(DetailUser.EXTRA_USERNAME, data.login)
                            it.putExtra(DetailUser.EXTRA_ID, data.id)
                            it.putExtra(DetailUser.EXTRA_URL, data.avatar_url)
                            startActivity(it)
                        }
                    }
                })
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}