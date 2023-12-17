package com.bangkit.cakrawala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.preferences.dataStore
import com.bangkit.cakrawala.ui.home.HomeActivity
import com.bangkit.cakrawala.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pref = AuthPreferences.getInstance(application.dataStore)
        val authData = pref.getCredential().asLiveData()


        Handler(Looper.getMainLooper()).postDelayed({

            authData.observe(this){
                if (it.token != "" && it.token?.isNotEmpty() == true){
                    val intentDetail = Intent(this, HomeActivity::class.java)
                    intentDetail.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    intentDetail.putExtra(HomeActivity.TOKEN_INTENT_KEY, it)
                    startActivity(intentDetail)
                } else {
                    startActivity(Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
            }

        }, 2000)



    }
}