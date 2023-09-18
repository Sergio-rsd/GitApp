package ru.sergiorsd.gitapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sergiorsd.gitapp.domain.entities.UserEntity

@Entity
data class UserEntityRoom(
    @PrimaryKey var id: Long = 0,
    var login: String = "",
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String = ""
) {
    fun mapDaoToEntity(): UserEntity {
        return UserEntity(login, id, avatarUrl)
    }
}