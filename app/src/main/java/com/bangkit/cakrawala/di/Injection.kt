package com.bangkit.cakrawala.di

import android.content.Context
import com.bangkit.cakrawala.data.database.CakrawalaDatabase
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.preferences.dataStore
import com.bangkit.cakrawala.data.repository.AuthRepository
import com.bangkit.cakrawala.data.repository.HistoryRepository
import com.bangkit.cakrawala.data.repository.PaymentRepository
import com.bangkit.cakrawala.data.repository.TextDetectionRepository
import com.bangkit.cakrawala.data.retrofit.ApiConfig

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService(context)
        return AuthRepository(apiService)
    }

    fun provideTransactionHistoryRepository(context: Context): PaymentRepository {
        val database = CakrawalaDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return PaymentRepository(apiService, database)
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val database = CakrawalaDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return HistoryRepository(apiService, database)
    }

    fun provideTextDetectionRepository(context: Context): TextDetectionRepository {
        val apiService = ApiConfig.getApiService(context)
        return TextDetectionRepository(apiService)
    }

    fun provideAuthPreferences(context: Context): AuthPreferences {
        return AuthPreferences.getInstance(context.applicationContext.dataStore)
    }
}