package com.example.inventoryapp.local

import androidx.room.*

@Dao
interface DaoUser {

    @Query( "SELECT * FROM user WHERE user_email=:email AND user_password=:password LIMIT 1")
    fun login(email: String?,password:String?):User

    @Query("SELECT * FROM user WHERE user_email=:email LIMIT 1")
    fun getUser(email: String?):User

    @Insert
    fun register(user: User)

}
