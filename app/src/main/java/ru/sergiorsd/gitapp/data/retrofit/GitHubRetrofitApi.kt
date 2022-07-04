package ru.sergiorsd.gitapp.data.retrofit

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface GitHubRetrofitApi {
    @GET("users")
//    fun getListUsers(): Call<List<UserEntity>>
//    fun getListUsers(): Observable<List<UserEntity>>
    fun getListUsers(): Single<List<UserEntity>>
}