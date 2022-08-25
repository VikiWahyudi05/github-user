package com.sib.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserFavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userFav: UserFav)

    @Query("DELETE FROM userfav WHERE username=:username")
    fun delete(username: String?)

    @Query("SELECT * from userfav ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserFav>>

}