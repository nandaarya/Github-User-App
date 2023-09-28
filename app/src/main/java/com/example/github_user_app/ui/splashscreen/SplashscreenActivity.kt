package com.example.github_user_app.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.github_user_app.data.datastore.SettingPreferences
import com.example.github_user_app.data.datastore.dataStore
import com.example.github_user_app.databinding.ActivitySplashscreenBinding
import com.example.github_user_app.ui.home.MainActivity
import com.example.github_user_app.ui.setting.SettingViewModel
import com.example.github_user_app.util.SettingViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTheme()

        binding.root.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }

    private fun setTheme() {
        val pref = SettingPreferences.getInstance(application.dataStore)

        val settingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}