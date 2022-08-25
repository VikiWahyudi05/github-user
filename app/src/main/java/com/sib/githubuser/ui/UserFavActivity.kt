package com.sib.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sib.githubuser.R
import com.sib.githubuser.adapter.UserFavAdapter
import com.sib.githubuser.databinding.ActivityUserFavBinding
import com.sib.githubuser.viewmodel.UserFavViewModel
import com.sib.githubuser.viewmodel.ViewModelFactory

class UserFavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavBinding
    private lateinit var adapter: UserFavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_fav)
        val actionbar = supportActionBar
        actionbar!!.title = "Favorites"
        actionbar.setDisplayHomeAsUpEnabled(true)
        binding = ActivityUserFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userFavViewModel = obtainViewModel(this@UserFavActivity)
        userFavViewModel.getAllUsers().observe(this, { userFavList ->
            if (userFavList != null) {
                adapter.setListUserFav(userFavList)
            }
        })

        adapter = UserFavAdapter()

        binding.apply {
            listUserFav.setHasFixedSize(true)
            listUserFav.layoutManager = LinearLayoutManager(this@UserFavActivity)
            listUserFav.adapter = adapter
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}