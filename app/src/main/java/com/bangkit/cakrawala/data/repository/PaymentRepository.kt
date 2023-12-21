package com.bangkit.cakrawala.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkit.cakrawala.data.database.CakrawalaDatabase
import com.bangkit.cakrawala.data.database.HistoryRemoteMediator
import com.bangkit.cakrawala.data.database.TransactionRemoteMediator
import com.bangkit.cakrawala.data.response.BuyPremiumResponse
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.data.response.PremiumResponse
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem
import com.bangkit.cakrawala.data.response.UploadTextResponse
import com.bangkit.cakrawala.data.retrofit.ApiService
import retrofit2.Call

class PaymentRepository (private val apiService: ApiService, private val cakrawalaDatabase: CakrawalaDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPaging() : LiveData<PagingData<TransactionHistoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = TransactionRemoteMediator(cakrawalaDatabase, apiService),
            pagingSourceFactory = {
                cakrawalaDatabase.transactionDao().getAllHistory()
            }
        ).liveData
    }

    suspend fun deleteAll(){
        cakrawalaDatabase.historyDao().deleteAll()
        cakrawalaDatabase.remoteKeysDao().deleteRemoteKeys()
    }

    fun buyPremium(id: Int): Call<BuyPremiumResponse> {
        return apiService.buyPremium(id)
    }

    fun getPremium() : Call<PremiumResponse>{
        return  apiService.getPremiumList()
    }

}