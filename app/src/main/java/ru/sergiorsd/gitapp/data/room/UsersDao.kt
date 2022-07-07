package ru.sergiorsd.gitapp.data.room

import androidx.room.*

@Dao
interface UsersDao {
    @Query("SELECT * FROM UserEntityRoom")
    fun getAllUsers(): List<UserEntityRoom>

    @Update
    fun updateUser(userEntityRoom: UserEntityRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntityRoom: UserEntityRoom)

    @Query("SELECT * FROM UserEntityRoom ")
    fun findByIdUser(userEntityRoom: UserEntityRoom)

    @Insert
    fun insertAllUsers(vararg userEntityRoom: UserEntityRoom)
}