package com.bangkit.cakrawala.data.retrofit


import com.bangkit.cakrawala.data.response.AuthResponse
import com.bangkit.cakrawala.data.response.HistoryResponse
import com.bangkit.cakrawala.data.response.UploadTextImageResponse
import com.bangkit.cakrawala.data.response.UploadTextPdfResponse
import com.bangkit.cakrawala.data.response.UploadTextResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("uploadText")
    fun uploadText(
        @Field("text") text: String,
    ): Call<UploadTextResponse>

    @Multipart
    @POST("upload")
    fun uploadFilePdf(
        @Part file: MultipartBody.Part,
    ) : Call<UploadTextPdfResponse>

    @Multipart
    @POST("upload")
    fun uploadFileImage(
        @Part file: MultipartBody.Part,
    ) : Call<UploadTextImageResponse>

    @GET("history")
    suspend fun getHistoriesPaging(
        @Query("page") page: Int? = null,
        @Query("itemsPerPage") size: Int? = null,
        ) : HistoryResponse

    @GET("history/{id}")
    fun deleteHistory(
        @Path("id") userId: String
    ) : Call<HistoryResponse>

}