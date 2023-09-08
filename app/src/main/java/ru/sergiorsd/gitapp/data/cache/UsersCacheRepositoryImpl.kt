package ru.sergiorsd.gitapp.data.cache

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.userCacheRepo

class UsersCacheRepositoryImpl : UsersCacheRepository {
    /*override fun getUsersCache(): Single<List<UserEntity>> = Single.create {
        it.onSuccess(cacheList)
    }*/
    //    override fun getUsersCache(): Single<List<UserEntity>> = userCacheRepo
    /*

        override fun getUsersCache(): Single<List<UserEntity>> = cacheList.map {
            UserEntity(it.login,it.id,it.avatarUrl)
        }
        private val cacheList = userCacheRepo
    */

    private val cacheList = userCacheRepo

    //    override fun getUsersCache(): Single<List<UserEntity>> = Single.just(userCacheRepo)
    override fun getUsersCache(): Single<List<UserEntity>> = Single.just(cacheList)
    override fun saveUsersToCache(listUsers: List<UserEntity>) {
        cacheList.clear()
        cacheList.addAll(listUsers)
    }


}