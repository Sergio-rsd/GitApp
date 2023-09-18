package ru.sergiorsd.gitapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.Subject
import ru.sergiorsd.gitapp.data.cache.UsersCacheRepository
import ru.sergiorsd.gitapp.data.room.UserEntityRoom
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository
import ru.sergiorsd.gitapp.ui.users.UserViewModel

fun convertUserLocalToCache(entityListRoom: List<UserEntityRoom>): List<UserEntity> {
    return entityListRoom.map {
        UserEntity(it.login, it.id, it.avatarUrl)
    }
}

/*

fun convertUserLocalToCache(entityListRoom: Single<List<UserEntityRoom>>): List<UserEntity> {
    return entityListRoom.map {
        UserEntity(it.login, it.id, it.avatarUrl)
    }
}
*/

fun convertListUsersCacheToLocal(listUsersCache: List<UserEntity>): List<UserEntityRoom> {
    return listUsersCache.map {
        UserEntityRoom(it.id, it.login, it.avatarUrl)
    }
}

fun convertUserEntityToUserLocal(user: UserEntity): UserEntityRoom {
    return UserEntityRoom(user.id, user.login,user.avatarUrl)
}

/*

class UserViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserViewModel(repository) as T
}
*/

// переписан ???
class UserViewModelFactory(
    private val repository: UsersRepository,
//    private val repoCache: UsersCacheRepository // дописан
) : ViewModelProvider.Factory {
    /*
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserViewModel(repository, repoCache) as T
        */
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserViewModel(repository) as T
}

fun <T : Any> Observable<T>.mutableObserve(): Subject<T> {
    return this as? Subject<T>
        ?: throw IllegalStateException("It is not Subject o_O")
}