package com.bangkit.cakrawala.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Database(
    entities = [HistoryResponseItem::class, RemoteKeys::class, TransactionHistoryResponseItem::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(ArrayListConverter::class)
abstract class CakrawalaDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: CakrawalaDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CakrawalaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CakrawalaDatabase::class.java, "cakrawala_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}


class ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String?): List<String>? {
        return Gson().fromJson<List<String>>(value, List::class.java)
    }
}