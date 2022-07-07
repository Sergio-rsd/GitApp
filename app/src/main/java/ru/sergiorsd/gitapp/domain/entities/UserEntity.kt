package ru.sergiorsd.gitapp.domain.entities

data class UserEntity(
    val login: String,
    val id: Long,
    val avatarUrl: String
)