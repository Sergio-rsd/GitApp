package ru.sergiorsd.gitapp.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

private const val BASE_URL = "https://api.github.com/"
private const val ERROR = "NO DATA OR NETWORK ERROR"

class UsersRepositoryImpl : UsersRepository {

    override fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        api.getListUsers().enqueue(object : Callback<List<UserEntity>> {
            override fun onResponse(
                call: Call<List<UserEntity>>,
                response: Response<List<UserEntity>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    onSuccess(responseBody)
                } else {
                    onError?.invoke(IllegalStateException(ERROR))
                }
                response.body()?.let { onSuccess(it) }
            }

            override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
                onError?.invoke(t)
            }
        })
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()
        )
        .build()
        .create(GitHubRetrofitApi::class.java)
}