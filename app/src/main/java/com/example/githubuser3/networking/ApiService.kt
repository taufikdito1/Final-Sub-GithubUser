package com.example.githubuser3.networking

import com.example.githubuser3.BuildConfig
import com.example.githubuser3.datasource.DetailUserResponse
import com.example.githubuser3.datasource.User
import com.example.githubuser3.datasource.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{username}")
    @Headers("Authorization: token $API_TOKEN")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("search/users")
    @Headers("Authorization: token $API_TOKEN")
    fun getUserBySearch(@Query("q") query: String): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_TOKEN")
    fun getListFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_TOKEN")
    fun getListFollowing(@Path("username") username: String): Call<ArrayList<User>>

    companion object{
        private const val API_TOKEN = BuildConfig.API_TOKEN
    }
}