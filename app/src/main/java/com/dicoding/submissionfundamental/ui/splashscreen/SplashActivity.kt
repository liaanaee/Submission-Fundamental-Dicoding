package com.dicoding.submissionfundamental.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissionfundamental.R
import com.dicoding.submissionfundamental.ui.main.MainActivity
import com.dicoding.submissionfundamental.ui.mode.SettingPreferences
import com.dicoding.submissionfundamental.ui.mode.ViewModelFactory
import com.dicoding.submissionfundamental.ui.mode.ViewModels
import com.dicoding.submissionfundamental.ui.mode.dataStore

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val pref = SettingPreferences.getInstance(applicationContext.dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(ViewModels::class.java)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                goToMainActivity()
            }, 3000L)
        }
    }

    fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
