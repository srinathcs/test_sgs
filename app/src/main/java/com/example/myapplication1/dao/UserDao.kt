package com.example.myapplication1.dao

import androidx.room.*
import com.example.myapplication1.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("UPDATE users SET password = :newPassword WHERE username = :username")
    suspend fun updateUserPassword(username: String, newPassword: String)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?
}
