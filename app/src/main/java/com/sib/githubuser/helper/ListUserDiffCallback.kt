package com.sib.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.sib.githubuser.model.User

class ListUserDiffCallback(
    private val mOldUserList: List<User>,
    private val mNewUserList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldUserList[oldItemPosition]
        val newUser = mOldUserList[newItemPosition]
        return oldUser.login == newUser.login && oldUser.avatar_url == newUser.avatar_url
    }
}