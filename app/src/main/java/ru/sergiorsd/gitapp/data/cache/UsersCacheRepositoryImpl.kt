package ru.sergiorsd.gitapp.data.cache

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.userCacheRepo

class UsersCacheRepositoryImpl : UsersCacheRepository {

    private val cacheList = userCacheRepo

    //    override fun getUsersCache(): Single<List<UserEntity>> = Single.just(userCacheRepo)
    override fun getUsersCache(): Single<List<UserEntity>> = Single.just(cacheList)

//    override fun getUsersCache(): Single<List<UserEntity>> = Single.fromObservable(cacheList)
    override fun saveUsersToCache(listUsers: List<UserEntity>) {
        cacheList.clear()
        cacheList.addAll(listUsers)
    }

}