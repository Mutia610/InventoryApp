package com.example.inventoryapp.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    val id : Int? = null,

    @ColumnInfo(name = "username")
    val user_name : String,

    @ColumnInfo(name = "user_email")
    val user_email : String,

    @ColumnInfo(name = "user_password")
    val user_password : String
)