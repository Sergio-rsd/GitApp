package ru.sergiorsd.gitapp.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

private const val BASE_URL = "https://api.github.com/"
private const val ERROR = "NO DATA OR NETWORK ERROR"

class UsersRepositoryImpl : UsersRepository {

    override fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        api.getListUsers().enqueue(object : Callback<List<UserEntityDTO>> {
            override fun onResponse(
                call: Call<List<UserEntityDTO>>,
                response: Response<List<UserEntityDTO>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    onSuccess(responseBody)
                } else {
                    onError?.invoke(IllegalStateException(ERROR))
                }
                response.body()?.let { onSuccess(it) }
            }

            override fun onFailure(call: Call<List<UserEntityDTO>>, t: Throwable) {
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