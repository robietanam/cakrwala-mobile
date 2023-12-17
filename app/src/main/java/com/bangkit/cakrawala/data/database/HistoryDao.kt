package com.bangkit.cakrawala.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.cakrawala.data.response.HistoryResponseItem


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<HistoryResponseItem>)

    @Query("SELECT * FROM history")
    fun getAllHistory(): PagingSource<Int, HistoryResponseItem>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}