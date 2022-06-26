package ru.sergiorsd.gitapp.ui.users

import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class UsersPresenter(
    private val usersRepo: UsersRepository
) : UsersContract.Presenter {
    private var view: UsersContract.View? = null

    private var usersList: List<UserEntity>? = null

    //    private var loadingError: Throwable? = null
    private var inProgress: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgress(inProgress)
        usersList?.let { view.showUsers(it) }
//        loadingError?.let { view.showError(it) }
    }

    override fun detach() {
        view = null
    }

    override fun onRefresh() {
        loadData()
    }

    override fun onUserClick(userEntity: UserEntity) {
        view?.openProfile(userEntity)
    }

    private fun loadData() {
        view?.showProgress(true)
        inProgress = true
        usersRepo.getUsers(
            onSuccess = {
                view?.showProgress(false)
                view?.showUsers(it)
                usersList = it

//                loadingError = null
                inProgress = false
            },
            onError = {
                view?.showProgress(false)
                view?.showError(it)
//                loadingError = it
                inProgress = false
            }
        )
    }
}