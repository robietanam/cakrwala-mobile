package com.bangkit.cakrawala.data.response

import com.google.gson.annotations.SerializedName

data class UploadTextPdfResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: UploadTextPdfResponseItem? = null
)

data class UploadTextPdfResponseItem(
    @SerializedName("sourceUrl") var sourceUrl: String? = null,
    @SerializedName("destinationUrl") var destinationUrl: String? = null,
    @SerializedName("extractedText") var extractedText: String? = null,
    @SerializedName("result") var result: Result? = Result()
)

data class Result(

    @SerializedName("ai_precentage") var aiPrecentage: Int? = null,
    @SerializedName("human_precentage") var humanPrecentage: Int? = null,
    @SerializedName("list_ai_sentences") var listAiSentences: ArrayList<String> = arrayListOf(),
    @SerializedName("result") var result: String? = null

)

data class UploadTextImageResponse(

    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: UploadTextImageResponseItem? = UploadTextImageResponseItem()

)

data class UploadTextImageResponseItem(

    @SerializedName("sourceUrl") var sourceUrl: String? = null,
    @SerializedName("destinationUrl") var destinationUrl: String? = null,
    @SerializedName("extractedText") var extractedText: String? = null,
    @SerializedName("result") var result: Result? = Result()

)


data class UploadTextResponse(

    @SerializedName("message") var message: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: UploadTextResponseItem? = UploadTextResponseItem()

)


data class UploadTextResponseItem(

    @SerializedName("rawUrl") var rawUrl: String? = null,
    @SerializedName("processedUrl") var processedUrl: String? = null,
    @SerializedName("extractedText") var extractedText: String? = null,
    @SerializedName("result") var result: Result? = Result()

)