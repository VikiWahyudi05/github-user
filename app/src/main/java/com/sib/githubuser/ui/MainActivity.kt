package com.sib.githubuser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sib.githubuser.R
import com.sib.githubuser.SettingPreferences
import com.sib.githubuser.adapter.ListUserAdapter
import com.sib.githubuser.databinding.ActivityMainBinding
import com.sib.githubuser.model.User
import com.sib.githubuser.viewmodel.SettingThemeViewModel
import com.sib.githubuser.viewmodel.SettingViewModelFactory
import com.sib.githubuser.viewmodel.UserViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingThemeViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingThemeViewModel::class.java
        )

        settingThemeViewModel.getThemeSetting().observe(this,
            { isDarkModeActive: Boolean ->
                binding.apply {
                    if (isDarkModeActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }

            }
        )

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })




        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
            recycleView.setHasFixedSize(true)
            recycleView.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            search.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getUser().observe(this, {
            if (it != null) {
                adapter.setListUser(it)
                isLoading(false)
            }
        })


    }


    private fun searchUser() {
        binding.apply {
            val query = search.text.toString()
            if (query.isEmpty()) return
            isLoading(true)
            viewModel.setUser(query)
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                Intent(this@MainActivity, UserFavActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }
            R.id.menu2 -> {
                Intent(this@MainActivity, SettingThemeActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }
            else -> return true
        }

    }


}
