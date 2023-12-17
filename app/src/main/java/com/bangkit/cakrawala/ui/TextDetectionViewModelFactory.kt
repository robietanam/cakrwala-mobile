package com.bangkit.cakrawala.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.repository.HistoryRepository
import com.bangkit.cakrawala.data.repository.TextDetectionRepository
import com.bangkit.cakrawala.di.Injection
import com.bangkit.cakrawala.ui.detection.text.TextDetectionViewModel
import com.bangkit.cakrawala.ui.history.HistoryViewModel

class TextDetectionViewModelFactory private constructor(private val textDetectionRepository: TextDetectionRepository, private val authPreferences: AuthPreferences) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: TextDetectionViewModelFactory? = null

        fun getInstance(context: Context): TextDetectionViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TextDetectionViewModelFactory(
                    Injection.provideTextDetectionRepository(context),
                    Injection.provideAuthPreferences(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(TextDetectionViewModel::class.java) -> {
                TextDetectionViewModel(textDetectionRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}