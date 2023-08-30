package ru.sergiorsd.gitapp.data.cache

import io.reactivex.rxjava3.core.Single
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class UsersCacheRepositoryImpl : UsersRepository {

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun getUsers(): Single<List<UserEntity>> {
        TODO("Not yet implemented")
    }

    override fun getUsersCache(): List<UserEntity>

    {
        TODO("Not yet implemented")
    }

}