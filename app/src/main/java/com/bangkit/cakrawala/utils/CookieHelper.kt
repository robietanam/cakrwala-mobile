package com.bangkit.cakrawala.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import com.bangkit.cakrawala.data.preferences.dataStore
import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.di.Injection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SendCookiesInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val token = runBlocking {
            val pref = Injection.provideAuthPreferences(context)
            pref.getCredential().first()
        }

        Log.d("MANATOKKENNYA", token.toString())

        builder.addHeader("Cookie", "jwt=${token.token}")

        return chain.proceed(builder.build())
    }
}