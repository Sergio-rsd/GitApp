package ru.sergiorsd.gitapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntityRoom(
    @PrimaryKey var id: Long,
    var login: String,
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String
)