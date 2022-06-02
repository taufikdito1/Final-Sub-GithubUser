package com.example.githubuser3.datasource

data class DetailUserResponse(
    val login : String,
    val name : String,
    val id : Int,
    val avatar_url : String,
    val followers : Int,
    val following : Int,
    val followers_url : String,
    val following_url : String,
    val company : String,
    val location : String
)
