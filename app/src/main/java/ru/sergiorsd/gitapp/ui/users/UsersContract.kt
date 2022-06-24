package ru.sergiorsd.gitapp.ui.users

import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO

interface UsersContract {

    interface View {

        fun showUsers(users : List<UserEntityDTO>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onRefresh()

    }
}