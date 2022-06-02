package com.example.githubuser3.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser3.datasource.DetailUserResponse
import com.example.githubuser3.datasource.favorite.FavDao
import com.example.githubuser3.datasource.favorite.FavDatabase
import com.example.githubuser3.datasource.favorite.FavUser
import com.example.githubuser3.networking.DataClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var favUserDao: FavDao?
    private var favUserDb: FavDatabase? = FavDatabase.getDatabase(application)

    init {
        favUserDao = favUserDb?.favDao()
    }

    fun setDetailUser(username: String) {
        DataClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("failure", t.message!!)
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addFavUser(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavUser(username,id,avatar_url)
            favUserDao?.addFavUser(user)
        }
    }

    suspend fun checkFavUser(id: Int) = favUserDao?.checkFavUser(id)

    fun removeFavUser(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favUserDao?.removeFavUser(id)
        }
    }
}