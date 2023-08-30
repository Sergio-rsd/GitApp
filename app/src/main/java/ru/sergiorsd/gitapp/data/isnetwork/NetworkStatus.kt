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

//        private val status: Observable<Boolean> = BehaviorSubject.create()
//    private val status: PublishSubject<Boolean> = PublishSubject.create()
//    private val status: AsyncSubject<Boolean> = AsyncSubject.create()

    init {
        status.onNext(false)
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    //Вызывается когда сеть есть сразу после запроса, либо когда появилась после отсутствия.
                    status.onNext(true)
//                    Toast.makeText(context, "Есть интернет", Toast.LENGTH_SHORT).show()
//                    status.onComplete()
                }

                override fun onUnavailable() {
                    //Вызывается, когда сеть не обнаружена после запроса
                    status.onNext(false)
//                    Toast.makeText(context, "Не обнаружен интернет", Toast.LENGTH_SHORT).show()
                }

                override fun onLost(network: Network) {
                    //Вызывается, когда сеть потеряна
                    status.onNext(false)
//                    Toast.makeText(context, "ПОТЕРЯН Интернет", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun isOnline(): Observable<Boolean> = status

    override fun isOnlineSingle(): Single<Boolean> = status.first(false)
//    override fun isOnlineSingle(): Single<Boolean> = status.firstOrError()

}