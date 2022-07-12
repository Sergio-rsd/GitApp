package ru.sergiorsd.gitapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntityRoom::class], version = 1, exportSchema = false)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}