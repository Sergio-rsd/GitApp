package ru.sergiorsd.gitapp.ui.users

import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO

interface UsersContract {

    interface View {

        fun showUsers(users : List<UserEntityDTO>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
        fun openProfile(userEntity: UserEntityDTO)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onRefresh()
        fun onUserClick(userEntity: UserEntityDTO)

    }
}