package com.sib.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.sib.githubuser.database.UserFav

class UserFavDiffCallback(
    private val mOldUserFavList: List<UserFav>,
    private val mNewUserFavList: List<UserFav>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserFavList[oldItemPosition].id == mNewUserFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUserFav = mOldUserFavList[oldItemPosition]
        val newUserFav = mOldUserFavList[newItemPosition]
        return oldUserFav.username == newUserFav.username && oldUserFav.avatar == newUserFav.avatar
    }

}