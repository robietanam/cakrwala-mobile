package com.bangkit.cakrawala.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.cakrawala.data.database.CakrawalaDatabase
import com.bangkit.cakrawala.data.database.HistoryRemoteMediator
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.data.response.UploadTextImageResponse
import com.bangkit.cakrawala.data.response.UploadTextPdfResponse
import com.bangkit.cakrawala.data.response.UploadTextResponse
import com.bangkit.cakrawala.data.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.Call


class HistoryRepository(private val apiService: ApiService, private val cakrawalaDatabase: CakrawalaDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPaging() : LiveData<PagingData<HistoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = HistoryRemoteMediator(cakrawalaDatabase, apiService),
            pagingSourceFactory = {
                cakrawalaDatabase.historyDao().getAllHistory()
            }
        ).liveData
    }

    suspend fun deleteAll(){
        cakrawalaDatabase.historyDao().deleteAll()
        cakrawalaDatabase.remoteKeysDao().deleteRemoteKeys()
    }

}