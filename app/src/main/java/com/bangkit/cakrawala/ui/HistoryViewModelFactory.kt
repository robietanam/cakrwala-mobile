package com.bangkit.cakrawala.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.repository.AuthRepository
import com.bangkit.cakrawala.data.repository.HistoryRepository
import com.bangkit.cakrawala.di.Injection
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.detection.text.TextDetectionViewModel
import com.bangkit.cakrawala.ui.history.HistoryViewModel
import com.bangkit.cakrawala.ui.login.LoginViewModel
import com.bangkit.cakrawala.ui.register.RegisterViewModel

class HistoryViewModelFactory private constructor(private val historyRepository: HistoryRepository, private val authPreferences: AuthPreferences) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null

        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(
                    Injection.provideHistoryRepository(context),
                    Injection.provideAuthPreferences(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}