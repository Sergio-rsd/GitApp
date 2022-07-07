package ru.sergiorsd.gitapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntityRoom(
    val login: String,
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)