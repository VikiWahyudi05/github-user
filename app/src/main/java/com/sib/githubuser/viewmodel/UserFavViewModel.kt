package com.sib.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sib.githubuser.database.UserFav
import com.sib.githubuser.repository.UserFavRepository

class UserFavViewModel(application: Application) : ViewModel() {
    private val mUserFavRepository: UserFavRepository = UserFavRepository(application)

    fun getAllUsers(): LiveData<List<UserFav>> = mUserFavRepository.getAllUsers()

}