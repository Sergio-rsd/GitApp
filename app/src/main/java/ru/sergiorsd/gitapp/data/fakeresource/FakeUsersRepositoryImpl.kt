package ru.sergiorsd.gitapp.data.fakeresource

import android.os.Handler
import android.os.Looper
import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO
import ru.sergiorsd.gitapp.domain.repository.UsersRepository

private const val FAKE_DELAY = 2_000L

class FakeUsersRepositoryImpl : UsersRepository {

    private val data: List<UserEntityDTO> = listOf(
        UserEntityDTO("mojombo", 1, "https://avatars.githubusercontent.com/u/1?v=4"),
        UserEntityDTO("defunkt", 2, "https://avatars.githubusercontent.com/u/2?v=4"),
        UserEntityDTO("pjhyett", 3, "https://avatars.githubusercontent.com/u/3?v=4")
    )

    override fun getUsers(
        onSuccess: (List<UserEntityDTO>) -> Unit,
        onError: ((Throwable) -> Unit)?
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess(data)
        }, FAKE_DELAY)
    }
}