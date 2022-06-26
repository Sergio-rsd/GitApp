package ru.sergiorsd.gitapp.ui.users

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ru.sergiorsd.gitapp.app
import ru.sergiorsd.gitapp.databinding.ActivityMainBinding
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.ui.profile.DetailsActivity

class MainActivity : AppCompatActivity(), UsersContract.View {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: UsersContract.Presenter

    private val adapter = UsersAdapter {
        presenter.onUserClick(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        presenter = extractPresenter()
        presenter.attach(this)
    }

    private fun extractPresenter(): UsersContract.Presenter {
        return lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: UsersPresenter(app.usersRepo)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): UsersContract.Presenter {
        return presenter
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    private fun initViews() {
        showProgress(false)

        binding.mainRefreshButton.setOnClickListener {
//            loadData()
            presenter.onRefresh()
        }

        initRecyclerView()

        showProgress(false)
    }

    private fun initRecyclerView() {
        binding.activityMainRecycler.layoutManager = LinearLayoutManager(this)
        binding.activityMainRecycler.adapter = adapter
    }

    override fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(inProgress: Boolean) {
        binding.progressBar.isVisible = inProgress
        binding.activityMainRecycler.isVisible = !inProgress
    }

    override fun openProfile(userEntity: UserEntity) {
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("id", userEntity.id.toString())
        intent.putExtra("login", userEntity.login)
        intent.putExtra("url", userEntity.avatarUrl)
        startActivity(intent)
    }
}