package com.bangkit.cakrawala.data.repository

import com.bangkit.cakrawala.data.response.AuthResponse
import com.bangkit.cakrawala.data.response.UploadTextImageResponse
import com.bangkit.cakrawala.data.response.UploadTextPdfResponse
import com.bangkit.cakrawala.data.response.UploadTextResponse
import com.bangkit.cakrawala.data.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.Call

class TextDetectionRepository(private val apiService: ApiService) {

    fun uploadText(text: String): Call<UploadTextResponse> {
        return apiService.uploadText(text)
    }

    fun uploadImage(image: MultipartBody.Part): Call<UploadTextImageResponse> {
        return apiService.uploadFileImage(image)
    }

    fun uploadPdf(pdf: MultipartBody.Part): Call<UploadTextPdfResponse> {
        return apiService.uploadFilePdf(pdf)
    }

}