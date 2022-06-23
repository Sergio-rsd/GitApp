package ru.sergiorsd.gitapp.ui.users

import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class UsersPresenter(private val usersRepo: UsersRepository) : UsersContract.Presenter {
    private var view: UsersContract.View? = null

    override fun attach(view: UsersContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        view?.showProgress(true)
        usersRepo.getUsers(
            onSuccess = {
                view?.showProgress(false)
                view?.showUsers(it)
            },
            onError = {
                view?.showProgress(false)
                view?.showError(it)
            }
        )
    }
}