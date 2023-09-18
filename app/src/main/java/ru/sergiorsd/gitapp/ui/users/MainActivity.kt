package ru.sergiorsd.gitapp.ui.users

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import ru.sergiorsd.gitapp.utils.MyThread
import ru.sergiorsd.gitapp.utils.UserViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myTreadHandler = MyThread()

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(app.usersRepo)
//            UserViewModelFactory(app.usersRepo, app.userCacheRepo)
    }

//    private val viewModel: UserViewModel by viewModels()

    /*

        private val viewModel: UserViewModel by lazy {
            ViewModelProvider(this)[UserViewModel::class.java]
        }
    */


    private val adapter = UsersAdapter {
        viewModel.onUserClick(it)
    }

    private val viewModelDisposable = CompositeDisposable()
    private lateinit var isNetwork: NetworkStatus
    private var isNetworkDisposable: Disposable? = null

    private var statusKey = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myTreadHandler.start()

        isNetwork = NetworkStatus(this) // TODO убрать потом

        /*
                // TODO ширина экрана
                val width = Resources.getSystem().displayMetrics.widthPixels
                Log.d(TAG, "width = $width px")
        */


        initViews()
//        initViewModel()

        viewModelDisposable.addAll(
            viewModel.progressLiveData.subscribe { showProgress(it) },
            viewModel.usersLiveData.subscribe { showUsers(it) },
            viewModel.errorLiveData.subscribe { showError(it) },
            viewModel.openProfileLiveData.subscribe { openProfile(it) }
        )

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

    }

    override fun onDestroy() {
        viewModelDisposable.dispose()
        isNetworkDisposable?.dispose()

        myTreadHandler.handler?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun initViews() {

//        isNetwork = NetworkStatus(this)

        showProgress(false)

        binding.mainRefreshButton.setOnClickListener {

//            isOnlineCheck(this)
            isInternetCheck()
//            isInternet()

        }

        initRecyclerView()

        showProgress(false)
    }

    private fun isInternetCheck() {
        Log.d(TAG, "isOnlineCheck - > ${isOnlineCheck(this)}")
        viewModel.onRefresh(isOnlineCheck((this)))
    }

    private fun isInternet() {

        isNetworkDisposable = isNetwork.isOnline()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {

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
                    if (!statusKey && !it) {
//                        statusKey = false
                        Log.d(TAG, "============== НЕТ интернета -> $it - statusKey: $statusKey ")

                        viewModel.onRefresh(statusKey)
                    }


                },
                onError = {
                    Log.d(TAG, "Ошибка = $it")
                }
            )

//        Log.d(TAG, "onRefresh() called $userCacheRepo")
    }

    private fun isOnlineCheck(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
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