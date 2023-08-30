package ru.sergiorsd.gitapp.data.room

import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersRoomRepository {
    fun getAllUsersFromLocal(): List<UserEntity>
    fun saveUserToLocal(user: UserEntity)
    fun saveListUsersToLocal (userList: List<UserEntity>)
}