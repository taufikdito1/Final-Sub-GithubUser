package com.example.githubuser3.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser3.datasource.favorite.FavDao
import com.example.githubuser3.datasource.favorite.FavDatabase
import com.example.githubuser3.datasource.favorite.FavUser

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var favUserDao: FavDao?
    private var favUserDb: FavDatabase? = FavDatabase.getDatabase(application)

    init {
        favUserDao = favUserDb?.favDao()
    }

    fun getFavoriteUser(): LiveData<List<FavUser>>?{
        return favUserDao?.getFavUser()
    }
}