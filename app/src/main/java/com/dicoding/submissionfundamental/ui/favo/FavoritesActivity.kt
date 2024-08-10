package com.dicoding.submissionfundamental.ui.favo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionfundamental.data.response.ItemsItem
import com.dicoding.submissionfundamental.databinding.ActivityFavoritesBinding
import com.dicoding.submissionfundamental.ui.main.SearchAdapter
import com.dicoding.submissionfundamental.ui.mode.SettingPreferences
import com.dicoding.submissionfundamental.ui.mode.ViewModelFactory
import com.dicoding.submissionfundamental.ui.mode.ViewModels
import com.dicoding.submissionfundamental.ui.mode.dataStore

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val adapterFav = SearchAdapter()
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var detailsViewModel: ViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavoGithub.layoutManager = layoutManager
        binding.rvFavoGithub.addItemDecoration(itemDecoration)
        binding.rvFavoGithub.adapter = adapterFav

        viewModel.getFavUser()?.observe(this) {
            val items = arrayListOf<ItemsItem>()
            it.map {
                val item = ItemsItem(id = it.id, login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapterFav.submitList(items)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ViewModels::class.java
        )
    }
}