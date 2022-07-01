package ru.sergiorsd.gitapp.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository
import ru.sergiorsd.gitapp.utils.SingleEventLiveData

class UserViewModel(
    private val usersRepo: UsersRepository
) : ViewModel() {
    val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    val progressLiveData: LiveData<Boolean> = MutableLiveData()
    val openProfileLiveData: LiveData<UserEntity> = SingleEventLiveData()

    fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        progressLiveData.mutable().postValue(true)
        usersRepo.getUsers(
            onSuccess = {
                progressLiveData.mutable().postValue(false)
                usersLiveData.mutable().postValue(it)
            },
            onError = {
                progressLiveData.mutable().postValue(false)
                errorLiveData.mutable().postValue(it)
            }
        )
    }

    fun onUserClick(userEntity: UserEntity) {
        openProfileLiveData.mutable().postValue(userEntity)

    }

    override fun onCleared() {
        super.onCleared()
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("It is not MutableLiveData o_O")
    }

}
class UserViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserViewModel(repository) as T
}