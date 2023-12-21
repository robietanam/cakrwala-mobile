package com.bangkit.cakrawala.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<TransactionHistoryResponseItem>)

    @Query("SELECT * FROM `transaction`")
    fun getAllHistory(): PagingSource<Int, TransactionHistoryResponseItem>

    @Query("DELETE FROM `transaction`")
    suspend fun deleteAll()
}