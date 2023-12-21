package com.bangkit.cakrawala.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.data.preferences.AuthPreferences
import com.bangkit.cakrawala.data.repository.AuthRepository
import com.bangkit.cakrawala.data.repository.PaymentRepository
import com.bangkit.cakrawala.di.Injection
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.detection.text.TextDetectionViewModel
import com.bangkit.cakrawala.ui.history.HistoryViewModel
import com.bangkit.cakrawala.ui.login.LoginViewModel
import com.bangkit.cakrawala.ui.payment.PaymentViewModel
import com.bangkit.cakrawala.ui.register.RegisterViewModel

class TransactionHistoryViewModelFactory private constructor(private val paymentRepository: PaymentRepository, private val authPreferences: AuthPreferences) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: TransactionHistoryViewModelFactory? = null

        fun getInstance(context: Context): TransactionHistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TransactionHistoryViewModelFactory(
                    Injection.provideTransactionHistoryRepository(context),
                    Injection.provideAuthPreferences(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(PaymentViewModel::class.java) -> {
                PaymentViewModel(paymentRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}