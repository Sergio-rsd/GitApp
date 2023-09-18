package ru.sergiorsd.gitapp.data.cache

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersCacheRepository {
    // Пустой репозиторий в памяти
    fun getUsersCache() : Single<List<UserEntity>>

    fun saveUsersToCache(listUsers : List<UserEntity>)
}