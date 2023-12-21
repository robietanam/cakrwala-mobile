package com.bangkit.cakrawala.data.source

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkit.cakrawala.data.database.CakrawalaDatabase
import com.bangkit.cakrawala.data.response.HistoryResponse
import com.bangkit.cakrawala.data.response.TransactionHistoryResponse
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem

class TransactionPagingSource(private val token: String, private val database: CakrawalaDatabase) :  PagingSource<Int, TransactionHistoryResponseItem>() {

     companion object {
        const val INITIAL_PAGE_INDEX = 1
        fun snapshot(items: TransactionHistoryResponse): PagingData<TransactionHistoryResponseItem> {
            return PagingData.from(items.data)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionHistoryResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionHistoryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX

            //val responseData = ApiConfig.getApiService(token).getStoriesPaging(location = 1, page = position, size = params.loadSize).listStory
            val responseData = database.transactionDao().getAllHistory()

            return responseData.load(params)
            /*LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )*/
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}