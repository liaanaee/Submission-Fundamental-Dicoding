package com.dicoding.submissionfundamental.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = if (position == 0)  FollowersFragment() else FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_POSITION, position)
            putString(ARG_USERNAME, username)
        }
        return fragment
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}