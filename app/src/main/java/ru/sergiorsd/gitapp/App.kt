package ru.sergiorsd.gitapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import ru.sergiorsd.gitapp.data.retrofit.UsersRepositoryImpl
import ru.sergiorsd.gitapp.data.room.UsersDao
import ru.sergiorsd.gitapp.data.room.UsersDataBase
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: UsersDataBase? = null
        private const val DB_NAME = "UsersGitHubLocal.db"

        fun getUsersRoomDao(): UsersDao {
            synchronized(UsersDataBase::class.java) {
                if (db == null) {
//                    if (appInstance == null) throw IllegalStateException("APP must not be null")
                    checkNotNull(appInstance) { "APP must not be null" }
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        UsersDataBase::class.java,
                        DB_NAME
                    )
//                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.usersDao()
        }
    }

    //        val usersRepo : UsersRepository by lazy { FakeUsersRepositoryImpl() }
    val usersRepo: UsersRepository by lazy { UsersRepositoryImpl() }

    //    val usersRepo: UsersRepository by lazy { UsersCacheRepositoryImpl() }
    var userCacheRepo: MutableList<UserEntity> = mutableListOf()
}

val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App