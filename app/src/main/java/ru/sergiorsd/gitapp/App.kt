package ru.sergiorsd.gitapp

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import ru.sergiorsd.gitapp.data.FakeUsersRepositoryImpl
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class App: Application() {
    val usersRepo : UsersRepository by lazy { FakeUsersRepositoryImpl() }
}
val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().applicationContext as App