package ru.sergiorsd.gitapp.utils

import ru.sergiorsd.gitapp.data.room.UserEntityRoom
import ru.sergiorsd.gitapp.domain.entities.UserEntity

fun convertUserLocalToCache(entityListRoom: List<UserEntityRoom>): List<UserEntity> {
    return entityListRoom.map {
        UserEntity(it.login, it.id, it.avatarUrl)
    }
}

fun convertListUsersCacheToLocal(listUsersCache : List<UserEntity>) : List<UserEntityRoom>{
    return listUsersCache.map {
        UserEntityRoom(it.id, it.login, it.avatarUrl)
    }
}
fun convertUserCacheToLocal(){

}