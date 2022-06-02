package com.example.githubuser3.datasource.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDao {
    @Insert
    suspend fun addFavUser(favUser: FavUser)

    @Query("SELECT * FROM fav_user")
    fun getFavUser(): LiveData<List<FavUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    suspend fun checkFavUser(id: Int): Int

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    suspend fun removeFavUser(id: Int): Int
}