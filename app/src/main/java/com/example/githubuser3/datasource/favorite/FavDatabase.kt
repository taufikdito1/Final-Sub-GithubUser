package com.example.githubuser3.datasource.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class],version = 1)
abstract class FavDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE : FavDatabase? = null

        fun getDatabase(context: Context): FavDatabase?{
            if (INSTANCE==null){
                synchronized(FavDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavDatabase::class.java,
                        "fav_database").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favDao(): FavDao
}