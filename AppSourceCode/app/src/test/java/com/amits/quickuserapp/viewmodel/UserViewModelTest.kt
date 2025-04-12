package com.amits.quickuserapp.viewmodel

import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.usecase.GetUserByIdUseCase
import com.amits.quickuserapp.domain.usecase.GetUsersUseCase
import com.amits.quickuserapp.presentation.UserUiState
import com.amits.quickuserapp.presentation.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class UserViewModelTest {

    private val getUsersUseCase: GetUsersUseCase = mockk()
    private val getUserByIdUseCase: GetUserByIdUseCase = mockk()
    private lateinit var viewModel: UserViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(getUsersUseCase, getUserByIdUseCase, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadUsers_emits_Error_when_user_list_is_empty() = runTest {
        coEvery { getUsersUseCase() } returns flowOf(emptyList())

        viewModel.loadUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(UserUiState.Error("Empty User List"), viewModel.state.value)
    }

    @Test
    fun test_loadUsers_emits_Success_when_users_are_fetched() = runTest {
        val users = listOf(
            User(
                id = 1,
                name = "Amit Srivastava",
                company = "Arris INC",
                username = "amits",
                email = "kks@example.com",
                address = "456 Oak Street",
                zip = "221002",
                state = "Utter Pradesh",
                country = "India",
                phone = "123-456-789",
                photo = "https://picsum.photos/200"
            )
        )
        coEvery { getUsersUseCase() } returns flowOf(users)

        viewModel.loadUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(UserUiState.Success(users), viewModel.state.value)
    }

    @Test
    fun test_loadUsers_emits_Error_when_exception_occurs() = runTest {
        val errorMessage = "Network error"
        coEvery { getUsersUseCase() } returns flow { throw Exception(errorMessage) }

        viewModel.loadUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(UserUiState.Error(errorMessage), viewModel.state.value)
    }

    @Test
    fun test_getUser_emits_null_when_user_not_found() = runTest {
        coEvery { getUserByIdUseCase(-1) } returns flowOf(null)

        viewModel.getUser(-1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.user.value)
    }

    @Test
    fun test_getUser_emits_user_when_valid_ID_provided() = runTest {
        val user = User(
            id = 1,
            name = "Amit Srivastava",
            company = "Arris INC",
            username = "amits",
            email = "kks@example.com",
            address = "456 Oak Street",
            zip = "221002",
            state = "Utter Pradesh",
            country = "India",
            phone = "123-456-789",
            photo = "https://picsum.photos/200"
        )
        coEvery { getUserByIdUseCase(1) } returns flowOf(user)

        viewModel.getUser(1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun test_setLoadingState () = runTest {
        viewModel.setLoadingState()

        assertEquals(UserUiState.Loading, viewModel.state.value)
    }
}