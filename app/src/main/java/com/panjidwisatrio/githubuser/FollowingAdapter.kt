package com.panjidwisatrio.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjidwisatrio.githubuser.databinding.ItemRowUserBinding

var followingFilter = ArrayList<UserData>()
class FollowingAdapter(private val listFollowing : ArrayList<UserData>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    init {
        followingFilter = listFollowing
    }

    class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user : UserData)= with(binding) {
            Glide.with(this.root)
                .load(user.avatar)
                .into(avatars)

            username.text = user.username
            company.text = user.company
            follower.text = user.followers
            following.text = user.following
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(listFollowing[position])
    }

    override fun getItemCount(): Int = followingFilter.size
}