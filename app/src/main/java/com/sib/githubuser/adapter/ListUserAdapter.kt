package com.sib.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sib.githubuser.databinding.ItemRowUserBinding
import com.sib.githubuser.helper.ListUserDiffCallback
import com.sib.githubuser.model.User

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListUser(listUser: ArrayList<User>) {
        val diffCallback = ListUserDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(var binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .into(holder.binding.ivItemPhoto)
        holder.binding.tvItemUsername.text = user.login

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)

        }

    }

    override fun getItemCount() = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)


    }


}