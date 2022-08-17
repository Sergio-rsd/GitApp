package ru.sergiorsd.gitapp.data.isnetwork

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NetworkStatus(context: Context) : IsNetworkStatus {
    private val status: BehaviorSubject<Boolean> = BehaviorSubject.create()
//    private val status: Observable<Boolean> = BehaviorSubject.create()

    init {
        status.onNext(false)
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    status.onNext(true)
                }

                override fun onUnavailable() {
                    status.onNext(false)
                }

                override fun onLost(network: Network) {
                    status.onNext(false)
                }
            })
    }

    override fun isOnline(): Observable<Boolean> = status

    override fun isOnlineSingle(): Single<Boolean> = status.first(false)
}