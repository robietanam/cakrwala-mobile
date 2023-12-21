package com.bangkit.cakrawala.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cakrawala.data.repository.AuthRepository
import com.bangkit.cakrawala.data.response.AuthResponse
import com.bangkit.cakrawala.data.response.ResponseStatus
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel(){

    private val _auth = MutableLiveData<AuthResponse>()
    val auth: LiveData<AuthResponse> = _auth

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val gson = Gson()

    fun register(username: String, email: String, password: String){
        _isLoading.value = true
        authRepository.register(username, email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _auth.value = response.body()
                } else {
                    val responseData = gson.fromJson(response.errorBody()?.string(), AuthResponse::class.java)
                    _auth.value = AuthResponse(status = ResponseStatus.Error, message = responseData.message ?: "Terdapat error, mohon coba lagi nanti")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _isLoading.value = false
                val message = t.message ?: "Gagal menjangkau API, mohon coba lagi nanti"
                _auth.value = AuthResponse(status = ResponseStatus.Error, message = message)
            }

        })
    }
}