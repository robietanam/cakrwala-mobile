package com.bangkit.cakrawala.data.repository

import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.data.response.AuthResponse
import com.bangkit.cakrawala.data.retrofit.ApiService
import retrofit2.Call

class AuthRepository(private val apiService: ApiService) {

    fun login(email: String, password: String) : Call<AuthResponse> {
        return apiService.login(email, password)
    }

    fun register(username: String, email: String, password: String) : Call<AuthResponse> {
        return apiService.register(username, email, password)
    }


}