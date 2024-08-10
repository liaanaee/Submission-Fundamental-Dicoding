package com.dicoding.submissionfundamental.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental.data.response.FollowersResponseItem
import com.dicoding.submissionfundamental.databinding.ItemGithubBinding

class FollowersAdapter(
    private val listFollowers: List<FollowersResponseItem>
) : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size

    inner class ViewHolder(private val binding: ItemGithubBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(follower: FollowersResponseItem){
            binding.tvUsernameGithub.text = follower.login
            Glide.with(itemView.context)
                .load(follower.avatarUrl)
                .into(binding.ivGithub)
        }
    }
}