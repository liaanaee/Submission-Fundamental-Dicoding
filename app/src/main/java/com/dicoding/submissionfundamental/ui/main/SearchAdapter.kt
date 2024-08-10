package com.dicoding.submissionfundamental.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental.data.response.ItemsItem
import com.dicoding.submissionfundamental.databinding.ItemGithubBinding
import com.dicoding.submissionfundamental.ui.detail.DetailActivity

class SearchAdapter : ListAdapter<ItemsItem, SearchAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentToDetail.putExtra("username", review.login)
            intentToDetail.putExtra("id", review.id)
            intentToDetail.putExtra("avatar_url", review.avatarUrl)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    class ViewHolder(val binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ItemsItem) {
            Glide.with(itemView.context).load(review.avatarUrl).into(binding.ivGithub)
            binding.tvUsernameGithub.text = review.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}