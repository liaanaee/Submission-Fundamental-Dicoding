package com.dicoding.submissionfundamental.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submissionfundamental.R
import com.dicoding.submissionfundamental.data.response.DetailUserResponse
import com.dicoding.submissionfundamental.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var username: String? = null
    private var id: Int? = null
    private var avatarUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        username = intent.getStringExtra("username")
        id = intent.getIntExtra("id", 0)
        avatarUrl = intent.getStringExtra("avatar_url")
        setViewPager()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username ?: "")
        binding.vpDetailGithub.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabDetailGithub, binding.vpDetailGithub) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (detailViewModel.usernameGithub.value == null) {
            detailViewModel.detailUserGithub(username.orEmpty())
            if (username != null) {
                detailViewModel.setUsernameGithub(username ?: "")
            }
        }

        detailViewModel.detailGithub.observe(this) {
            setDetailGithub(it)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var _isFavorited = false
        CoroutineScope(Dispatchers.IO).launch {
            val countFavorite = detailViewModel.checkFavo(id!!)
            withContext(Dispatchers.Main) {
                if (countFavorite != null) {
                    if (countFavorite > 0) {
                        binding.toggleButton2.isChecked = true
                        _isFavorited = true
                    } else {
                        binding.toggleButton2.isChecked = false
                        _isFavorited = false
                    }
                }
            }
        }

        binding.toggleButton2.setOnClickListener {
            _isFavorited = !_isFavorited
            if (_isFavorited) {
                detailViewModel.insertToFavorite(username!!, id!!, avatarUrl!!)
            } else {
                detailViewModel.deleteFromFavorite(id!!)
            }
        }
    }

    private fun showLoading(b: Boolean) {
        binding.pgGithub.visibility = if (b) View.VISIBLE else View.GONE
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username ?: "")
        val viewPager: ViewPager2 = binding.vpDetailGithub

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabDetailGithub
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailGithub(detailGithub: DetailUserResponse) {
        Glide.with(this@DetailActivity).load(detailGithub.avatarUrl)
            .into(binding.ivDetailGithub)
        binding.tvNameDetail.text = detailGithub.name
        binding.tvUsernameDetail.text = detailGithub.login
        binding.tvFollowersDetail.text = "${detailGithub.followers} Followers"
        binding.tvFollowingDetail.text = "${detailGithub.following} Following"
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}