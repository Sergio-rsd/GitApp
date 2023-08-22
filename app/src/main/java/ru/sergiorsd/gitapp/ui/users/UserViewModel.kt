package ru.sergiorsd.gitapp.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.sergiorsd.gitapp.data.isnetwork.NetworkStatus
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class UserViewModel(
    private val usersRepo: UsersRepository
) : ViewModel() {

    val usersLiveData: Observable<List<UserEntity>> = BehaviorSubject.create()
    val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    val progressLiveData: Observable<Boolean> = BehaviorSubject.create()
    val openProfileLiveData: Observable<UserEntity> = PublishSubject.create()
//    private val context = app.applicationContext

    //    val isNetwork: Observable<Boolean> = BehaviorSubject.create()
    private lateinit var isNetwork: NetworkStatus


    /*
    val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    val progressLiveData: LiveData<Boolean> = MutableLiveData()
    val openProfileLiveData: LiveData<UserEntity> = SingleEventLiveData()
*/

    fun onRefresh() {
        // TODO здесь проверку на наличие сети?
//        val context = App().app.baseContext
//        val context = App().app.applicationContext
//        val context =  Context as App
        /*

                val context = App().applicationContext

                isNetwork = NetworkStatus(context)
                isNetwork.isOnline().subscribe {
                    Toast.makeText(context, "$it - Signal", Toast.LENGTH_SHORT).show()
                }
        */

        loadData()
    }

    private fun loadData() {
        /*
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
*/
        progressLiveData.mutableObserve().onNext(true)

        usersRepo.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressLiveData.mutableObserve().onNext(false)
                    usersLiveData.mutableObserve().onNext(it)
                },
                onError = {
                    progressLiveData.mutableObserve().onNext(false)
                    errorLiveData.mutableObserve().onNext(it)
                }
            )
    }

    fun onUserClick(userEntity: UserEntity) {
        (openProfileLiveData as Subject).onNext(userEntity)
//        openProfileLiveData.mutable().onNext(userEntity)

    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("It is not MutableLiveData o_O")
    }

    private fun <T : Any> Observable<T>.mutableObserve(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("It is not Subject o_O")
    }
}

class UserViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserViewModel(repository) as T
}