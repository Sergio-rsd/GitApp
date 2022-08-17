package ru.sergiorsd.gitapp.data.isnetwork

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IsNetworkStatus {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}