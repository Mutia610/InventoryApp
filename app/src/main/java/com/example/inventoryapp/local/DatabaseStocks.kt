package com.example.inventoryapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stocks::class,User::class], version = 4)
abstract class DatabaseStocks : RoomDatabase() {

    abstract fun stocksDao(): DaoStocks
    abstract fun userDao(): DaoUser

    companion object {
        private var INSTANCE: DatabaseStocks? = null
        fun getInstance(context: Context): DatabaseStocks? {
            synchronized(this) {
                var instance: DatabaseStocks? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseStocks::class.java,
                        "dbstocks.db"
                    ).build()
                }

                return instance
            }
        }
    }
}