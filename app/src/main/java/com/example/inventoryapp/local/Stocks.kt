package com.example.inventoryapp.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class Stocks(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int? = null,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "nama_barang")
    val nama_barang : String?,

    @ColumnInfo(name = "jumlah_stock")
    val jumlah_stock : String?,

    @ColumnInfo(name = "keterangan")
    val keterangan : String?

)