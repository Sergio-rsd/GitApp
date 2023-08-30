package ru.sergiorsd.gitapp.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersRepository {
    // Callback
    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
    // RxJava
    fun getUsers() : Single<List<UserEntity>>

    // Пустой репозиторий в памяти
    fun getUsersCache() : List<UserEntity>
}