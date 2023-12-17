package com.bangkit.cakrawala.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.R
import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.databinding.ActivityHomeBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.history.HistoryFragment
import com.bangkit.cakrawala.ui.login.LoginActivity
import com.bangkit.cakrawala.ui.settings.SettingsFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var tokenViewModel: TokenViewModel

    var authData : Auth? = null
    override fun onBackPressed() {
        super.onBackPressed()
        binding.bottomNavigationView.menu.getItem(1).apply {
            isChecked = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager

        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val settingsFragment = SettingsFragment()

        val fragmentHome = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragmentHome !is HomeFragment) {
            fragmentManager
                .beginTransaction()
                .replace(R.id.home_fragment_container_view, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

        tokenViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(this))[TokenViewModel::class.java]


        tokenViewModel.getToken().observe(this){
            if (it?.token != null){
                authData = it
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        binding.bottomNavigationView.apply { 
            background = null
            menu.getItem(1).apply {
                isCheckable = false
                isChecked = true
            }

        }

        binding.fab.setOnClickListener {
            binding.bottomNavigationView.menu.getItem(1).apply {
                isChecked = true
            }
            setFragment(homeFragment)
        }


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.History -> setFragment(historyFragment)
                R.id.Settings -> setFragment(settingsFragment)
            }
            true

        }
    }

    private fun setFragment(fragment: Fragment) {
        if (fragment is HomeFragment){
            supportFragmentManager.popBackStack("LOL", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            supportFragmentManager.popBackStack("LOL", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.home_fragment_container_view, fragment)
                addToBackStack("LOL")
                commit()
            }
        }

    }


}