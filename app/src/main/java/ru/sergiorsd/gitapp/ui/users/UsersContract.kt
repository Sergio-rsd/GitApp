package ru.sergiorsd.gitapp.ui.users

import ru.sergiorsd.gitapp.domain.entities.UserEntity

interface UsersContract {

    interface View {

        fun showUsers(users : List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
        fun openProfile(userEntity: UserEntity)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onRefresh()
        fun onUserClick(userEntity: UserEntity)

    }
}