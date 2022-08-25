package com.sib.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.sib.githubuser.database.UserFav
import com.sib.githubuser.repository.UserFavRepository

class UserFavAddDeleteViewModel(application: Application) : ViewModel() {
    private val mUserFavRepository: UserFavRepository = UserFavRepository(application)

    fun insert(userFav: UserFav) {
        mUserFavRepository.insert(userFav)
    }

    fun delete(userFav: UserFav) {
        mUserFavRepository.delete(userFav)
    }
}