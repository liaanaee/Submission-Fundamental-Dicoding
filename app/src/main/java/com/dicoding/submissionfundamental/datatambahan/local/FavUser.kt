package com.dicoding.submissionfundamental.datatambahan.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Fav_User")
data class FavUser(
    @PrimaryKey
    val id: Int,
    var username: String = " ",
    var avatarUrl: String
) : Serializable
