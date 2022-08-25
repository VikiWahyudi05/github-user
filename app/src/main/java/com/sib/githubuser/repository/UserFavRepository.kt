package com.sib.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sib.githubuser.database.UserFavDao
import com.sib.githubuser.database.UserFav
import com.sib.githubuser.database.UserFavRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavRepository(application: Application) {
    private val mUserFavDao: UserFavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavRoomDatabase.getDatabase(application)
        mUserFavDao = db.userFavDao()
    }

    fun getAllUsers(): LiveData<List<UserFav>> = mUserFavDao.getAllUsers()


    fun insert(userFav: UserFav) {
        executorService.execute { mUserFavDao.insert(userFav) }
    }

    fun delete(userFav: UserFav) {
        executorService.execute { mUserFavDao.delete(userFav.username) }
    }


}