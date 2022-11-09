package com.hoon.newsarticleapp.view.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hoon.newsarticleapp.BaseActivity
import com.hoon.newsarticleapp.BaseViewModel
import com.hoon.newsarticleapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<BaseViewModel, ActivityMainBinding>() {

    // this activity not used Viewmodel yet. (set BaseViewModel)
    override val viewModel = object : BaseViewModel() {}

    override fun getViewBinding() =
        ActivityMainBinding.inflate(layoutInflater)

    override fun observeData() {}

    override fun initViews() = with(binding) {
        // set bottom navigation view
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }
}