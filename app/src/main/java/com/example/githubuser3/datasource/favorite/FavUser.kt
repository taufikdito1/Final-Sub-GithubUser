package com.example.githubuser3.datasource.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_user")
data class FavUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
): Serializable
