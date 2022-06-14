package ru.sergiorsd.gitapp.ui.users

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sergiorsd.gitapp.app
import ru.sergiorsd.gitapp.databinding.ActivityMainBinding
import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = UsersAdapter()

    private val usersRepo: UsersRepository by lazy { app.usersRepo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        showProgress(false)

        binding.mainRefreshButton.setOnClickListener {
            loadData()
        }

        initRecyclerView()
    }

    private fun loadData() {
        showProgress(true)
        usersRepo.getUsers(
            onSuccess = {
                showProgress(false)
                onDataLoaded(it)
            },
            onError = {
                showProgress(false)
                onError(it)
            }
        )
    }

    private fun onDataLoaded(data: List<UserEntityDTO>) {
        adapter.setData(data)
    }

    private fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        binding.activityMainRecycler.layoutManager = LinearLayoutManager(this)
        binding.activityMainRecycler.adapter = adapter
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.activityMainRecycler.isVisible = !inProgress
    }
}