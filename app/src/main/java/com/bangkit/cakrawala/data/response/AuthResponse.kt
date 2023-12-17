package com.bangkit.cakrawala.data.response

import com.google.gson.annotations.SerializedName



data class AuthResponse (
    @field:SerializedName("message")
    val message : String? = null,

    @field:SerializedName("status")
    val status : String? = null,

    @field:SerializedName("data")
    val data : Auth? = null
)

data class Auth(
    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("username")
    val userName: String? = null,
)

class AuthStatus {
    companion object {
        const val SUCCESS = "Sukses"
        const val Error = "Error"
    }
}