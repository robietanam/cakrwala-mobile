package com.bangkit.cakrawala.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.response.Auth
import kotlinx.coroutines.launch

class TokenViewModel(private val pref: AuthPreferences) : ViewModel() {



    fun getToken(): LiveData<Auth> {
        return pref.getCredential().asLiveData()
    }

    fun saveToken(auth: Auth) {
        viewModelScope.launch {
            pref.saveCredential(Auth(token = auth.token, userName = auth.userName, userId = auth.userId))
        }
    }


    fun resetToken() {
        viewModelScope.launch {
            pref.saveCredential(Auth())
        }
    }
}
