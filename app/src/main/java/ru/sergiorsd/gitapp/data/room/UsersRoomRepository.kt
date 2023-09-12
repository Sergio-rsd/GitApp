package ru.sergiorsd.gitapp.data.room

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersRoomRepository {
    fun getAllUsersFromLocal(): Single<List<UserEntity>>

//    fun getAllUsersFromLocal(): List<UserEntity>
    fun saveUserToLocal(user: UserEntity)
    fun saveListUsersToLocal (userList: List<UserEntity>)
}