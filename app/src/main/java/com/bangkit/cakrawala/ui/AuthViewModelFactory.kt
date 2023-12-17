package com.bangkit.cakrawala.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.repository.AuthRepository
import com.bangkit.cakrawala.di.Injection
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.login.LoginViewModel
import com.bangkit.cakrawala.ui.register.RegisterViewModel

class AuthViewModelFactory private constructor(private val authRepository: AuthRepository, private val authPreferences: AuthPreferences) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null

        fun getInstance(context: Context): AuthViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideAuthPreferences(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(TokenViewModel::class.java) -> {
                TokenViewModel(authPreferences) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}