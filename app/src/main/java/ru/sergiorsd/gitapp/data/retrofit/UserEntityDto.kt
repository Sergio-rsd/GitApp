package ru.sergiorsd.gitapp.data.retrofit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName
import ru.sergiorsd.gitapp.domain.entities.UserEntity

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEntityDto(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String
) {
    fun mapDtoToEntity(): UserEntity {
        return UserEntity(login, id, avatarUrl)
    }

    fun mapEntityToDto(userEntity: UserEntity): UserEntityDto {
        return UserEntityDto(
            userEntity.login,
            userEntity.id,
            userEntity.avatarUrl
        )
    }
}