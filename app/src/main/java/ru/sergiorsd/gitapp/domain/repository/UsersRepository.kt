package ru.sergiorsd.gitapp.domain.repository

import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersRepository {

    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )
}