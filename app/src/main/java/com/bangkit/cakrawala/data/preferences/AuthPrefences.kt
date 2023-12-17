package com.bangkit.cakrawala.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.cakrawala.data.response.Auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val USER_ID = stringPreferencesKey("user_ID")
    private val USERNAME = stringPreferencesKey("username")

    fun getCredential(): Flow<Auth> {
        return dataStore.data.map { preferences ->
            Auth(token = preferences[TOKEN_KEY], userId = preferences[USER_ID], userName = preferences[USERNAME])
        }
    }

    suspend fun saveCredential(data: Auth) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = data.token ?: ""
            preferences[USER_ID] = data.userId ?: ""
            preferences[USERNAME] = data.userName ?: ""
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

    }
}