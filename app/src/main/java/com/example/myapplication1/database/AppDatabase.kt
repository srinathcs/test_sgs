package com.example.myapplication1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication1.dao.UserDao
import com.example.myapplication1.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    object DatabaseProvider {
        private var database: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (database == null) {
                synchronized(AppDatabase::class) {
                    database = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "my-database"
                    ).build()
                }
            }
            return database!!
        }
    }
}