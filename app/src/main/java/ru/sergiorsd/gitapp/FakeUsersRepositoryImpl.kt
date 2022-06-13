package ru.sergiorsd.gitapp

import android.os.Handler
import android.os.Looper
import ru.sergiorsd.gitapp.api.UserEntityDTO

private const val FAKE_DELAY = 1_000L

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