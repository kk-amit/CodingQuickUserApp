package com.amits.quickuserapp.usecase

import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import com.amits.quickuserapp.domain.usecase.GetUserByIdUseCase
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
class GetUserByIdUseCaseTest {

    private val repository: UserRepository = mockk()
    private lateinit var getUserByIdUseCase: GetUserByIdUseCase

    @Before
    fun setUp() {
        getUserByIdUseCase = GetUserByIdUseCase(repository)
    }

    @Test
    fun returnsUser_whenUserExists() = runTest {
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
        coEvery { repository.getUserById(1) } returns flowOf(user)

        val result = getUserByIdUseCase(1).toList()

        assertEquals(listOf(user), result)
    }

    @Test
    fun returnsNull_whenUserDoesNotExist() = runTest {
        coEvery { repository.getUserById(2) } returns flowOf(null)

        val result = getUserByIdUseCase(2).toList()

        assertEquals(listOf(null), result)
    }

    @Test
    fun throwsException_whenRepositoryFails() = runTest {
        val exception = Exception("Repository error")
        coEvery { repository.getUserById(3) } throws exception

        try {
            getUserByIdUseCase(3).toList()
            fail("Expected an Exception to be thrown")
        } catch (e: Exception) {
            assertEquals("Repository error", e.message)
        }
    }
}