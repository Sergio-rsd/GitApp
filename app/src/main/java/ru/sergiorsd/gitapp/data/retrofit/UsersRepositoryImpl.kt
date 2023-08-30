package ru.sergiorsd.gitapp.data.retrofit

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

private const val BASE_URL = "https://api.github.com/"
private const val ERROR = "NO DATA OR NETWORK ERROR"

class UsersRepositoryImpl : UsersRepository {

    // не используется пока
    override fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        /*
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
        */

        api.getListUsers().subscribeBy(
            onSuccess = { usersList ->
                onSuccess.invoke(usersList.map { dto ->
                    dto.mapDtoToEntity()
                })
            },
            onError = {
                onError?.invoke(it)
            }
        )
    }

    override fun getUsers(): Single<List<UserEntity>> = api.getListUsers().map { usersList ->
        usersList.map { dto ->
            dto.mapDtoToEntity()
        }
    }

    override fun getUsersCache(): List<UserEntity> {
        TODO("Not yet implemented")


    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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