package ru.sergiorsd.gitapp

import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.domain.repository.UsersRepository
import ru.sergiorsd.gitapp.ui.users.UsersContract
import ru.sergiorsd.gitapp.ui.users.UsersPresenter

class SearchPresenterTest {

    private lateinit var presenter: UsersPresenter

    @Mock
    private lateinit var repository: UsersRepository

    @Mock
    private lateinit var viewContract: UsersContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = UsersPresenter(repository)
    }

    @Test // работает или нет: запрос к репозитарию
    fun onRefreshGitHub_Test() {
//        val responseResult = listOf(mock(UserEntity::class.java))
//        val res: (List<UserEntity>) -> Unit = { responseResult }
//        val res: (List<UserEntity>) -> Unit = { }
//        val errorResponse = mock(Throwable::class.java)
//        val err: (Throwable) -> Unit = { errorResponse }
//        val err: (Throwable) -> Unit = {  }


        presenter.onRefresh()

        verify(repository, times(1))
//            .getUsers(onSuccess = {responseResult})
//            .getUsers(res, err)
//            .getUsers({ responseResult }, { errorResponse })
//        verify(viewContract, times(1)).showUsers(responseResult)
//        verify(repository, times(1)).getUsers()
//            .getUsers(viewContract.showUsers() , null)
    }

    @Test // проверка на успешный ответ
    fun responseServer_Test() {

        val response = mock(Response::class.java) as Response<List<UserEntity>>
        val responseResult = listOf(mock(UserEntity::class.java))

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(responseResult)

        presenter.onRefresh()
        // not work
        verify(viewContract, times(1)).showUsers(responseResult)
//        verify(repository, times(1)).getUsers(onSuccess = {responseResult},null)
//

    }

    @Test // проверка ошибки при обращении к репозитарию
    fun responseServerError_Test() {
        val response = mock(Response::class.java) as Response<List<UserEntity>>
        `when`(response.isSuccessful).thenReturn(false)

        assertFalse(response.isSuccessful)
    }

}