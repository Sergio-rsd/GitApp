package ru.sergiorsd.gitapp

import ru.sergiorsd.gitapp.api.UserEntityDTO

class UsersRepositoryImpl : UsersRepository {
    override fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        TODO("Not yet implemented")
    }
}