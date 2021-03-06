package ru.sergiorsd.gitapp.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface GitHubRetrofitApi {
    @GET("users")
    fun getListUsers(): Call<List<UserEntity>>
}