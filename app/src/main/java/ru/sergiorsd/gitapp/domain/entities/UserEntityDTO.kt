package ru.sergiorsd.gitapp.domain.entities


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEntityDTO(
    val login: String,
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String
)