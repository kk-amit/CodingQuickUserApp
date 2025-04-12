package com.amits.quickuserapp.usecase

import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import com.amits.quickuserapp.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUsersUseCaseTest {

    private val repository: UserRepository = mockk()
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun emitsUsersList_whenRepositoryReturnsUsers() = runTest {
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
            ),
            User(
                id = 2,
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
        coEvery { repository.getUsers() } returns flowOf(users)

        val result = getUsersUseCase().toList()

        assertEquals(listOf(users), result)
    }

    @Test
    fun emitsEmptyList_whenRepositoryReturnsNoUsers() = runTest {
        coEvery { repository.getUsers() } returns flowOf(emptyList())

        val result = getUsersUseCase().toList()

        assertEquals(listOf(emptyList<User>()), result)
    }

    @Test
    fun throwsException_whenRepositoryFails() = runTest {
        val exception = Exception("Repository error")
        coEvery { repository.getUsers() } throws exception

        try {
            getUsersUseCase().toList()
            fail("Expected an Exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Repository error", e.message)
        }
    }
}