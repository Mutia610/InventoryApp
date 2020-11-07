package com.example.inventoryapp.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoStocks {

    @Query(value = "SELECT * FROM stocks")
    fun getAll(): List<Stocks>

    @Query("SELECT count(id) as total_stock from stocks")
    fun getTotal(): String?

    @Insert
    fun insert(stocks: Stocks)

    @Update
    fun update(stocks: Stocks?)

    @Delete
    fun delete(stocks: Stocks?)
}