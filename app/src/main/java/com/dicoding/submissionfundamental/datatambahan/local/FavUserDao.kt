package com.dicoding.submissionfundamental.datatambahan.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(note: FavUser)

    @Query("SELECT * from Fav_User ORDER BY id ASC")
    fun getFavUser(): LiveData<List<FavUser>>

    @Query("SELECT count (*) from Fav_User where Fav_User.id = :id")
    suspend fun CekUser(id: Int): Int

    @Query("DELETE FROM Fav_User WHERE Fav_User.id = :id")
    suspend fun removeFav(id: Int): Int
}