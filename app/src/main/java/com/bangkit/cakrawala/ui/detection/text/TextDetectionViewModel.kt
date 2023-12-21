package com.bangkit.cakrawala.ui.detection.text

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.cakrawala.data.repository.TextDetectionRepository
import com.bangkit.cakrawala.data.response.ResponseStatus
import com.bangkit.cakrawala.data.response.UploadTextImageResponse
import com.bangkit.cakrawala.data.response.UploadTextPdfResponse
import com.bangkit.cakrawala.data.response.UploadTextResponse
import com.bangkit.cakrawala.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TextDetectionViewModel(private val textDetectionRepository: TextDetectionRepository) : ViewModel() {

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> = _groupId

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseText = MutableLiveData<UploadTextResponse>()
    val responseText: LiveData<UploadTextResponse> = _responseText

    private val _responseImage = MutableLiveData<UploadTextImageResponse>()
    val responseImage: LiveData<UploadTextImageResponse> = _responseImage

    private val _responsePdf = MutableLiveData<UploadTextPdfResponse>()
    val responsePdf: LiveData<UploadTextPdfResponse> = _responsePdf

    fun setGroupId(id: Int){
        _groupId.value = id
    }

    fun uploadText(text: String){
        _isLoading.value = true
        val client = textDetectionRepository.uploadText(text)
        client.enqueue(object : Callback<UploadTextResponse> {
            override fun onResponse(
                call: retrofit2.Call<UploadTextResponse>,
                response: Response<UploadTextResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _responseText.value = response.body()
                } else {
                    _responseText.value = UploadTextResponse(status = ResponseStatus.Error)
                }
            }

            override fun onFailure(call: retrofit2.Call<UploadTextResponse>, t: Throwable) {
                _isLoading.value = false
                _responseText.value = UploadTextResponse(status = ResponseStatus.Error)
            }
        })
    }

    fun uploadImage(imageFile: File){
        _isLoading.value = true
        val requestImageFile = imageFile.reduceFileImage().asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        val client = textDetectionRepository.uploadImage(multipartBody)
        client.enqueue(object : Callback<UploadTextImageResponse> {
            override fun onResponse(
                call: retrofit2.Call<UploadTextImageResponse>,
                response: Response<UploadTextImageResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _responseImage.value = response.body()
                } else {
                    _responseImage.value = UploadTextImageResponse(status = ResponseStatus.Error)
                }
            }

            override fun onFailure(call: retrofit2.Call<UploadTextImageResponse>, t: Throwable) {
                _isLoading.value = false
                _responseImage.value = UploadTextImageResponse(status = ResponseStatus.Error)
            }
        })
    }


    fun uploadPdf(pdfFile: File){
        _isLoading.value = true

        val requestPdfFile = pdfFile.asRequestBody("application/pdf".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            pdfFile.name,
            requestPdfFile
        )
        val client = textDetectionRepository.uploadPdf(multipartBody)
        client.enqueue(object : Callback<UploadTextPdfResponse> {
            override fun onResponse(
                call: retrofit2.Call<UploadTextPdfResponse>,
                response: Response<UploadTextPdfResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _responsePdf.value = response.body()
                } else {
                    _responsePdf.value = UploadTextPdfResponse(status = ResponseStatus.Error)
                }
            }

            override fun onFailure(call: retrofit2.Call<UploadTextPdfResponse>, t: Throwable) {
                _isLoading.value = false
                _responsePdf.value = UploadTextPdfResponse(status = ResponseStatus.Error)
            }
        })
    }





}