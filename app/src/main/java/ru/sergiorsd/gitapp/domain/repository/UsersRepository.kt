package ru.sergiorsd.gitapp.domain.repository

import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO

interface UsersRepository {

    fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}