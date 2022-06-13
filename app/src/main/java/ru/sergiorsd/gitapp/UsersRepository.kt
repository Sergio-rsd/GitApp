package ru.sergiorsd.gitapp

import ru.sergiorsd.gitapp.api.UserEntityDTO

interface UsersRepository {

    fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}