package ru.sergiorsd.gitapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sergiorsd.gitapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = UsersAdapter()

    private val usersRepo: UsersRepository = FakeUsersRepositoryImpl()
//    private val usersRepo: UsersRepository = UsersRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRefreshButton.setOnClickListener {
//            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show()
            usersRepo.getUsers(
                onSuccess = adapter::setData,
                onError = {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.activityMainRecycler.layoutManager = LinearLayoutManager(this)
        binding.activityMainRecycler.adapter = adapter


    }
}