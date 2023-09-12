package ru.sergiorsd.gitapp.data.room

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.utils.convertUserEntityToUserLocal

class LocalRepositoryImpl(private val localDataSource: UsersDao) : UsersRoomRepository {
    //    override fun getAllUsersFromLocal(): List<UserEntity> {
    override fun getAllUsersFromLocal(): Single<List<UserEntity>> {
        return localDataSource.getAllUsers().map { userListRoom ->
            userListRoom.map { dao ->
                dao.mapDaoToEntity()
            }
        }
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
