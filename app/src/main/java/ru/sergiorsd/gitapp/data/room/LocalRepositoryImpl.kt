package ru.sergiorsd.gitapp.data.room

import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.utils.convertUserEntityToUserLocal
import ru.sergiorsd.gitapp.utils.convertUserLocalToCache

class LocalRepositoryImpl(private val localDataSource: UsersDao) : UsersRoomRepository {
    override fun getAllUsersFromLocal(): List<UserEntity> {
        return convertUserLocalToCache(localDataSource.getAllUsers())
    }

    override fun saveListUsersToLocal(userList: List<UserEntity>) {
        for (user in userList) {
            localDataSource.insertUser(convertUserEntityToUserLocal(user))
        }
    }

    override fun saveUserToLocal(user: UserEntity) {
        TODO("Not yet implemented")
    }
}
