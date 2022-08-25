package com.sib.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sib.githubuser.database.UserFav
import com.sib.githubuser.databinding.ItemRowUserBinding
import com.sib.githubuser.helper.UserFavDiffCallback
import com.sib.githubuser.ui.UserDetailActivity

class UserFavAdapter : RecyclerView.Adapter<UserFavAdapter.UserFavViewHolder>() {

    private val listUserFav = ArrayList<UserFav>()
    fun setListUserFav(listUserFav: List<UserFav>) {
        val diffCallback = UserFavDiffCallback(this.listUserFav, listUserFav)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUserFav.clear()
        this.listUserFav.addAll(listUserFav)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavViewHolder, position: Int) {
        holder.bind(listUserFav[position])
    }

    override fun getItemCount(): Int {
        return listUserFav.size
    }

    inner class UserFavViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userFav: UserFav) {
            with(binding) {
                tvItemUsername.text = userFav.username
                Glide.with(itemView.context)
                    .load(userFav.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivItemPhoto)
                itemView.setOnClickListener {
                    Intent(itemView.context, UserDetailActivity::class.java).also {
                        it.putExtra(UserDetailActivity.EXTRA_USERNAME, userFav.username)
                        it.putExtra(UserDetailActivity.EXTRA_ISFAV, false)
                        itemView.context.startActivity(it)
                    }
                }
            }
        }
    }
}