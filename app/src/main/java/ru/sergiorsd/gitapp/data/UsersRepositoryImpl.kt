package ru.sergiorsd.gitapp.data

import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class UsersRepositoryImpl : UsersRepository {
    override fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}