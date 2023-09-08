package ru.sergiorsd.gitapp.data.room

import androidx.room.*

@Dao
interface UsersDao {
    @Query("SELECT * FROM UserEntityRoom")
    fun getAllUsers(): List<UserEntityRoom>

    @Update
    fun updateUser(userEntityRoom: UserEntityRoom)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userEntityRoom: UserEntityRoom)
/*

    @Query("SELECT * FROM UserEntityRoom ")
    fun findByIdUser(userEntityRoom: UserEntityRoom)
*/
// Не понятно как пользоваться
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(vararg userEntityRoom: UserEntityRoom)
}