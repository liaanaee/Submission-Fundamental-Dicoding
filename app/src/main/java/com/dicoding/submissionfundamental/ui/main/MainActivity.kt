package com.dicoding.submissionfundamental.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionfundamental.R
import com.dicoding.submissionfundamental.data.response.ItemsItem
import com.dicoding.submissionfundamental.databinding.ActivityMainBinding
import com.dicoding.submissionfundamental.ui.favo.FavoritesActivity
import com.dicoding.submissionfundamental.ui.mode.SettingPreferences
import com.dicoding.submissionfundamental.ui.mode.ThemeActivity
import com.dicoding.submissionfundamental.ui.mode.ViewModelFactory
import com.dicoding.submissionfundamental.ui.mode.ViewModels
import com.dicoding.submissionfundamental.ui.mode.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)

        binding.toolbarGithub.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_theme -> {
                    val intent = Intent(this, ThemeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_favorite->{
                    val intent = Intent(this, FavoritesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        mainViewModel.itemsGithub.observe(this) {
            setItemGithub(it)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchViewGithub.setupWithSearchBar(searchBarGithub)
            searchViewGithub.editText
                .setOnEditorActionListener { tvListGithub, actionId, event ->
                    searchBarGithub.setText(searchViewGithub.text)
                    searchViewGithub.hide()
                    mainViewModel.listGithub(searchViewGithub.text.toString())
                    false
                }
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ViewModels::class.java
        )

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
    private fun showLoading(b: Boolean) {
        binding.pgGithub.visibility = if (b) View.VISIBLE else View.GONE
    }

    private fun setItemGithub(it: List<ItemsItem>) {
        val adapterGithub = SearchAdapter()
        adapterGithub.submitList(it)
        binding.rvGithub.adapter = adapterGithub
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                Intent(this, FavoritesActivity::class.java).also{
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}