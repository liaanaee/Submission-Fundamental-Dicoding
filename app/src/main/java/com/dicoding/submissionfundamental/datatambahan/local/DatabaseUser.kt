package com.dicoding.submissionfundamental.datatambahan.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class], version = 1, exportSchema = false)
abstract class DatabaseUser : RoomDatabase() {

    companion object {
        var INSTANCE: DatabaseUser? = null

        fun getDatabase(context: Context): DatabaseUser {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseUser::class.java, "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
    abstract fun favUserDao(): FavUserDao
}
