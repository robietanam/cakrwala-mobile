package com.bangkit.cakrawala.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bangkit.cakrawala.data.database.ArrayListConverter
import com.google.gson.annotations.SerializedName


data class HistoryResponse(
    @field:SerializedName("data")
    val data: List<HistoryResponseItem> = listOf(),

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message : String? = null,

    @field:SerializedName("pagination")
    val pagination: Pagination? = null
)


@Entity(tableName = "history")
data class HistoryResponseItem(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("raw_file")
    var rawFile: String? = null,

    @field:SerializedName("processed_file")
    var processedFile: String? = null,

    @field:SerializedName("user_id")
    var userId: Int? = null,

    @field:SerializedName("raw_filename")
    var rawFilename: String? = null,

    @field:SerializedName("processed_filename")
    var processedFilename: String? = null,

    @field:SerializedName("created_at")
    var createdAt: String? = null,

    @field:SerializedName("result_generated")
    var resultGenerated: String? = null,

    @field:SerializedName("ai_percentage")
    var aiPercentage: Double? = null,

    @field:SerializedName("human_percentage")
    var humanPercentage: Double? = null,

    @field:SerializedName("list_ai_sentences")
    var listAiSentences: List<String>? = null,

    @field:SerializedName("upload_id") var uploadId: Int? = null
)

data class Pagination (
    @field:SerializedName("page") var page: Int? = null,
    @field:SerializedName("itemsPerPage") var itemsPerPage : Int? = null

)
