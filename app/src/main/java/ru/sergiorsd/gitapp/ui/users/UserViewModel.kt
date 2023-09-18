package ru.sergiorsd.gitapp.ui.users

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.sergiorsd.gitapp.App
import ru.sergiorsd.gitapp.data.cache.UsersCacheRepository
import ru.sergiorsd.gitapp.data.cache.UsersCacheRepositoryImpl
import ru.sergiorsd.gitapp.data.retrofit.UsersRepositoryImpl
import ru.sergiorsd.gitapp.data.room.LocalRepositoryImpl
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository
import ru.sergiorsd.gitapp.userCacheRepo
import ru.sergiorsd.gitapp.utils.ListConstant.TAG
import ru.sergiorsd.gitapp.utils.mutableObserve

class UserViewModel(
    /*
    private val usersRepo: UsersRepository,
    private val usersCacheRepo:UsersCacheRepository, // добавлено
    private val usersLocal : UsersRoomRepository // добавлено
*/
    private val usersRepo: UsersRepository = UsersRepositoryImpl(),
    private val usersCache: UsersCacheRepository = UsersCacheRepositoryImpl(), // добавлено
    private val usersLocal: LocalRepositoryImpl = LocalRepositoryImpl(App.getUsersRoomDao())// добавлено

) : ViewModel() {

    val usersLiveData: Observable<List<UserEntity>> = BehaviorSubject.create()
    val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    val progressLiveData: Observable<Boolean> = BehaviorSubject.create()
    val openProfileLiveData: Observable<UserEntity> = PublishSubject.create()

    private val usersRetrofit: Observable<List<UserEntity>> = BehaviorSubject.create()
    private val userRoom: Observable<List<UserEntity>> = BehaviorSubject.create()

    /*
val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData()
val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
val progressLiveData: LiveData<Boolean> = MutableLiveData()
val openProfileLiveData: LiveData<UserEntity> = SingleEventLiveData()
*/

    @SuppressLint("CheckResult")
    fun onRefresh(isNetwork: Boolean) {

        Log.d(TAG, "onRefresh() called with: isNetwork = $isNetwork")

        // TODO здесь разветвление и логика выбора репозитория

        if (userCacheRepo.isEmpty()) {
            if (isNetwork) {
                loadDataRetrofit()
                usersRetrofit.subscribeBy {
                    usersCache.saveUsersToCache(it)
                    saveFromCacheToLocal(it)
                    loadData()
                }
//                loadData()
            } else {
                loadDataLocal()
                userRoom.subscribeBy {
//                    Log.d(TAG, "userRoom ====> $it")
                    usersCache.saveUsersToCache(it)
                    loadData()
                }
            }
        }
//        loadData()

        /*
        if (userCacheRepo.isEmpty()) {
            if (isNetwork) {
                loadDataRetrofit()
                loadData()
            } else {
                loadDataLocal()
                loadData()
            }
        } else {
            loadData()
        }
*/
        /*

                if (isNetwork) {
                    // пустой cache?
                    // скачать из retrofit
                    loadDataRetrofit()
                    // записать в cache
        //            loadData()
                    // записать в room
        //            loadData()

                } else {
                    Log.d(TAG, "Брать после ROOM")
                    loadData()
                }
        */
    }

    @SuppressLint("CheckResult")
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
// TODO и что делать с cache ???

        /*
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

                */

        usersCache.getUsersCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressLiveData.mutableObserve().onNext(false)
                    usersLiveData.mutableObserve().onNext(it)
                    Log.d(TAG, "loadData() из CACHE $userCacheRepo")
                },
                onError = {
                    progressLiveData.mutableObserve().onNext(false)
                    errorLiveData.mutableObserve().onNext(it)
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun loadDataLocal() {
        Log.d(TAG, "=========== loadDataLocal() called")

        usersLocal.getAllUsersFromLocal()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    progressLiveData.mutableObserve().onNext(false)
                    userRoom.mutableObserve().onNext(it)
//                    Log.d(TAG, "loadDataLocal() ++++++ > $it")
                },
                onError = {
                    progressLiveData.mutableObserve().onNext(false)
                    errorLiveData.mutableObserve().onNext(it)
                }
            )

    }

    private fun saveFromCacheToLocal(listUsers: List<UserEntity>) {
        Thread {
            usersLocal.saveListUsersToLocal(listUsers)
        }.start()
    }

    @SuppressLint("CheckResult")
    private fun loadDataRetrofit() {
        progressLiveData.mutableObserve().onNext(true)

//        val userRetrofitGet: Disposable =
        usersRepo.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            /*
            .doAfterSuccess {
//                userCacheRepo.removeAll(it)
//                userCacheRepo.addAll(it)

//                usersCache.saveUsersToCache(it)

            }*/
            .subscribeBy(
                onSuccess = {
                    progressLiveData.mutableObserve().onNext(false)
                    usersRetrofit.mutableObserve().onNext(it)
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
}

