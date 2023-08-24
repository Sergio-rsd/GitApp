package ru.sergiorsd.gitapp.ui.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.sergiorsd.gitapp.app
import ru.sergiorsd.gitapp.data.isnetwork.NetworkStatus
import ru.sergiorsd.gitapp.databinding.ActivityMainBinding
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.ui.profile.DetailsActivity
import ru.sergiorsd.gitapp.utils.ListConstant.TAG


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(app.usersRepo)
    }

    private val adapter = UsersAdapter {
        viewModel.onUserClick(it)
    }

    private val viewModelDisposable = CompositeDisposable()
    private lateinit var isNetwork: NetworkStatus
    private var isNetworkDisposable: Disposable? = null
    private var lisStatus: MutableList<Boolean> = mutableListOf()
    private var statusKey = false
//    private var statusTrue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isNetwork = NetworkStatus(this)

        initViews()
//        initViewModel()

        viewModelDisposable.addAll(
            viewModel.progressLiveData.subscribe { showProgress(it) },
            viewModel.usersLiveData.subscribe { showUsers(it) },
            viewModel.errorLiveData.subscribe { showError(it) },
            viewModel.openProfileLiveData.subscribe { openProfile(it) }
        )

        /*

                isNetwork.isOnline().subscribe {
                    if (statusKey == false && it == true) {
                        Log.d(TAG, "ЕСТЬ ИНТЕРНЕТ - statusKey: $statusKey ")
                        statusKey = true
                    }
                    if (statusKey == true && it == false) {
                        Log.d(TAG, "НЕТ интернета - statusKey: $statusKey ")
                        statusKey = false
                    }
        */
        /*
                    if (it) {
                        statusTrue += 1
                        statusFalse = 0
        //                statusKey = true
                    } else {
                        statusFalse += 1
                        statusTrue = 0
                    }
         *//*


            */
        /*
                                if (statusTrue !== 0) {
                                    Log.d(TAG, "ЕСТЬ ИНТЕРНЕТ - True: $statusTrue ")
                                } else {
                                    Log.d(TAG, "НЕТ интернета - False: $statusFalse ")
                                }
                    *//*


*/
        /*
                    Toast.makeText(
                        this,
        //                "$it - Интернет и True: $statusTrue и False $statusFalse и statusKey: $statusKey",
                        "$it - Интернет и statusKey: $statusKey",
                        Toast.LENGTH_SHORT
                    ).show()
                    *//*

//            Log.d(TAG, "$it - Интернет и True: $statusTrue и False $statusFalse и statusKey: $statusKey")
            Log.d(TAG, "$it - Интернет и statusKey: $statusKey")
        }
*/

        // Способ без класса, только на библиотеке
        /*
                ReactiveNetwork
                    .observeNetworkConnectivity(applicationContext)
        //            .observeInternetConnectivity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { isConnectedToInternet ->
                        run {
        //                    val stat = isConnectedToInternet.state().toString()
                            val stat = isConnectedToInternet.available()
                            Log.d(TAG, "isConnectedToInternet1 = $stat")
        //                    Log.d(TAG, "isConnectedToInternet2 = $isConnectedToInternet")

                            if (!statusKey && stat) {
                                statusKey = true
                                Log.d(TAG, "ЕСТЬ ИНТЕРНЕТ - statusKey: $statusKey ")
                                statusTrue += 1
                            }
                            if (statusKey && !stat) {
                                statusKey = false
                                Log.d(TAG, "НЕТ интернета - statusKey: $statusKey ")
                            }
                            lisStatus.add(stat)

                            Log.d(
                                TAG,
                                "$stat - Интернет и statusKey: $statusKey и statusTrue $statusTrue и список $lisStatus и ПОСЛЕДНЕЕ СОСТОЯНИЕ ${lisStatus[lisStatus.size - 1]}"
                            )


                        }
                    }
         */


        /*

                ReactiveNetwork
                    .observeNetworkConnectivity(applicationContext)
                    .switchMapSingle { connectivity: Connectivity? -> ReactiveNetwork.checkInternetConnectivity() }
        //            .flatMapSingle { connectivity: Connectivity? -> ReactiveNetwork.checkInternetConnectivity() }
        //            .switchMap { connectivity: Connectivity? -> ReactiveNetwork.checkInternetConnectivity() }
        //            .map { connectivity: Connectivity? -> ReactiveNetwork.checkInternetConnectivity() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { isConnected: Boolean? ->
                        Log.d(TAG, "isConnected = $isConnected")
                    }
        */


//        val checkNet = ReactiveNetwork.create()
        /*
        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                Log.d(TAG, "isConnected = $it")
            }
*/


        // Главное
        /*
        isNetwork.isOnline().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //            .lastElement()
//                    .takeLast(1)
            .subscribeBy {
                if (!statusKey && it) {
                    statusKey = true
                    Log.d(TAG, "ЕСТЬ ИНТЕРНЕТ - statusKey: $statusKey ")
                    statusTrue += 1
                }
                if (statusKey && !it) {
                    statusKey = false
                    Log.d(TAG, "НЕТ интернета - statusKey: $statusKey ")
                }
                lisStatus.add(it)

                Toast.makeText(this, "$it - Интернет", Toast.LENGTH_SHORT).show()

                Log.d(
                    TAG,
                    "$it - Интернет и statusKey: $statusKey и statusTrue $statusTrue и список $lisStatus и ПОСЛЕДНЕЕ СОСТОЯНИЕ ${lisStatus[lisStatus.size - 1]}"
                )
            }
*/

        // Single Internet check
        /*

                isNetwork.isOnlineSingle().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        Toast.makeText(this, "$it - Single СИГНАЛ", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "$it - Single СИГНАЛ")
                    }
        */


    }

    /*

        private fun initViewModel() {


            viewModel.progressLiveData.subscribe { showProgress(it) }
            viewModel.usersLiveData.subscribe { showUsers(it) }
            viewModel.errorLiveData.subscribe { showError(it) }
            viewModel.openProfileLiveData.subscribe { openProfile(it) }
            */
    /*
                  viewModel.progressLiveData.observe(this) { showProgress(it) }
                  viewModel.usersLiveData.observe(this) { showUsers(it) }
                  viewModel.errorLiveData.observe(this) { showError(it) }
                  viewModel.openProfileLiveData.observe(this) { openProfile(it) }
                  */
    /*
    }
*/
    override fun onDestroy() {
        viewModelDisposable.dispose()
        isNetworkDisposable?.dispose()

        super.onDestroy()
    }

    private fun initViews() {
        showProgress(false)

        binding.mainRefreshButton.setOnClickListener {
/*
            val tt = isInternet()
            Log.d(TAG, "initViews() called $tt")
            */

            isInternet()

//            viewModel.onRefresh()

        }

        initRecyclerView()

        showProgress(false)
    }

    private fun isInternet() {

        isNetworkDisposable = isNetwork.isOnline().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (!statusKey && it) {
                    statusKey = true
                    Log.d(TAG, "ЕСТЬ ИНТЕРНЕТ - statusKey: $statusKey ")
//                    statusTrue += 1
                    viewModel.onRefresh(statusKey)

                }
                if (statusKey && !it) {
                    statusKey = false
                    Log.d(TAG, "НЕТ интернета - statusKey: $statusKey ")

                    viewModel.onRefresh(statusKey)
                }

                /*

                Toast.makeText(this, "$it - Интернет", Toast.LENGTH_SHORT).show()

                lisStatus.add(it)

                Log.d(
                    TAG,
                    "$it - Интернет и statusKey: $statusKey и statusTrue $statusTrue и список $lisStatus и ПОСЛЕДНЕЕ СОСТОЯНИЕ ${lisStatus[lisStatus.size - 1]}"
                )
                */
            }

    }

    private fun initRecyclerView() {
        binding.activityMainRecycler.layoutManager = LinearLayoutManager(this)
        binding.activityMainRecycler.adapter = adapter
    }

    private fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.activityMainRecycler.isVisible = !inProgress
    }

    private fun openProfile(userEntity: UserEntity) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("id", userEntity.id.toString())
        intent.putExtra("login", userEntity.login)
        intent.putExtra("url", userEntity.avatarUrl)
        startActivity(intent)
    }
}