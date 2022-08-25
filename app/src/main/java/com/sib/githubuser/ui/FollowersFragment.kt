package com.sib.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sib.githubuser.R
import com.sib.githubuser.adapter.ListUserAdapter
import com.sib.githubuser.databinding.FragmentFollowBinding
import com.sib.githubuser.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)


        adapter = ListUserAdapter()

        binding?.apply {
            recycleView.setHasFixedSize(true)
            recycleView.layoutManager = LinearLayoutManager(activity)
            recycleView.adapter = adapter
        }

        isLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListUser(it)
                isLoading(false)
            }
        })
    }

    private fun isLoading(state: Boolean) {
        binding?.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}