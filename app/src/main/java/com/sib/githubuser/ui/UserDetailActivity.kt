package com.sib.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.sib.githubuser.R
import com.sib.githubuser.adapter.SectionPagerAdapter
import com.sib.githubuser.database.UserFav
import com.sib.githubuser.databinding.ActivityDetailUserBinding
import com.sib.githubuser.viewmodel.UserFavAddDeleteViewModel
import com.sib.githubuser.viewmodel.ViewModelFactory

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: UserDetailViewModel

    private var userFav: UserFav? = null
    private lateinit var userFavAddDeleteViewModel: UserFavAddDeleteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userFavAddDeleteViewModel = obtainViewModel(this)

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()
        val isFav = intent.getBooleanExtra(EXTRA_ISFAV, true)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserDetailViewModel::class.java)


        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvDetailName.text = it.name
                    tvDetailNumberOfFollowers.text = it.followers.toString()
                    tvDetailNumberOfFollowing.text = it.following.toString()
                    tvDetailCompany.text = it.company
                    tvDetailLocation.text = it.location
                    tvDetailNumberOfRepository.text = it.public_repos

                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(ivDetailPhoto)

                    val login = it.login
                    val avatar = it.avatar_url
                    userFav = UserFav()
                    userFav.let {
                        userFav?.username = login
                        userFav?.avatar = avatar
                    }

                    if (isFav) {
                        fabFav.setOnClickListener {
                            try {
                                userFavAddDeleteViewModel.insert(userFav as UserFav)
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.add_success),
                                    Snackbar.LENGTH_SHORT
                                ).setAction("Action", null).show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = intent
                                    intent.putExtra(UserDetailActivity.EXTRA_USERNAME, username)
                                    intent.putExtra(UserDetailActivity.EXTRA_ISFAV, false)
                                    finish()
                                    startActivity(intent);
                                }, 2000)
                            } catch (e: Exception) {
                                Snackbar.make(
                                    binding.root,
                                    "Error: ${e.message}",
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("Action", null).show()
                            }
                        }

                    } else {

                        fabFav.setImageResource(R.drawable.ic_delete)
                        fabFav.setOnClickListener {
                            try {
                                userFavAddDeleteViewModel.delete(userFav as UserFav)
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.delete_success),
                                    Snackbar.LENGTH_SHORT
                                ).setAction("Action", null).show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    val intent = intent
                                    intent.putExtra(UserDetailActivity.EXTRA_USERNAME, username)
                                    intent.putExtra(UserDetailActivity.EXTRA_ISFAV, true)
                                    finish()
                                    startActivity(intent);
                                }, 2000)
                            } catch (e: Exception) {
                                Snackbar.make(
                                    binding.root,
                                    "Error: ${e.message}",
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("Action", null).show()
                            }

                        }




                    }

                }

            }

        })

        val actionBar = supportActionBar
        actionBar?.title = username
        actionBar?.setDisplayHomeAsUpEnabled(true)


        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }


    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavAddDeleteViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ISFAV = "extra_isfav"
    }


}