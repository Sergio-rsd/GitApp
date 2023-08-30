package ru.sergiorsd.gitapp.data.room

import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.utils.convertListUsersCacheToLocal
import ru.sergiorsd.gitapp.utils.convertUserLocalToCache

class LocalRepositoryImpl(private val localDataSource: UsersDao) : UsersRoomRepository {
    override fun getAllUsersFromLocal(): List<UserEntity> {
        return convertUserLocalToCache(localDataSource.getAllUsers())

    }

    override fun saveListUsersToLocal(userList: List<UserEntity>) {
        convertListUsersCacheToLocal(userList)
    }

    override fun saveUserToLocal(user: UserEntity) {
        TODO("Not yet implemented")
    }
}
