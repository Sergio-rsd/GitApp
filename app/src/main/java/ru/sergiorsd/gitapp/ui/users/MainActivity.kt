package ru.sergiorsd.gitapp.ui.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sergiorsd.gitapp.app
import ru.sergiorsd.gitapp.databinding.ActivityMainBinding
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.ui.profile.DetailsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(app.usersRepo)
    }

    private val adapter = UsersAdapter {
        viewModel.onUserClick(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initViewModel()

    }

    private fun initViewModel() {

        viewModel.progressLiveData.observe(this) { showProgress(it) }
        viewModel.usersLiveData.observe(this) { showUsers(it) }
        viewModel.errorLiveData.observe(this) { showError(it) }
        viewModel.openProfileLiveData.observe(this) { openProfile(it) }

    }

    private fun initViews() {
        showProgress(false)

        binding.mainRefreshButton.setOnClickListener {
            viewModel.onRefresh()
        }

        initRecyclerView()

        showProgress(false)
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