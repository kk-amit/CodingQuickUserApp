package com.amits.quickuserapp.repository

import com.amits.quickuserapp.data.mapper.toEntity
import com.amits.quickuserapp.data.repository.UserRepositoryImpl
import com.amits.quickuserapp.data.repository.datasource.LocalUserDataSource
import com.amits.quickuserapp.data.repository.datasource.RemoteUserDataSource
import com.amits.quickuserapp.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private var localUserDataSource: LocalUserDataSource? = null
    private var remoteUserDataSource: RemoteUserDataSource? = null
    private var userRepository: UserRepositoryImpl? = null

    @Before
    fun setUp() {
        localUserDataSource = mockk()
        remoteUserDataSource = mockk()
        userRepository = UserRepositoryImpl(remoteUserDataSource!!, localUserDataSource!!)
    }

    @After
    fun tearDown() {
        localUserDataSource = null
        remoteUserDataSource = null
        userRepository = null
    }

    @Test
    fun emitsLocalUsers_whenLocalDataIsAvailable() = runTest {
        val localUsers = listOf(
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
        coEvery { localUserDataSource?.getUsers() } returns localUsers
        coEvery { remoteUserDataSource?.fetchUsers() } returns flow { emit(emptyList()) }

        val result = userRepository?.getUsers()?.toList()

        assertEquals(listOf(localUsers,localUsers), result)
    }

    @Test
    fun emitsUpdatedLocalUsers_whenRemoteDataIsFetched() = runTest {
        val localUsers = listOf(
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
        val remoteUsers = listOf(
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
        coEvery { localUserDataSource?.getUsers() } returns localUsers andThen (localUsers + remoteUsers)
        coEvery { remoteUserDataSource?.fetchUsers() } returns flow { emit(remoteUsers.map { it.toEntity() }) }
        coEvery { localUserDataSource?.saveUsers(remoteUsers.map { it.toEntity() }) } returns Unit

        val result = userRepository?.getUsers()?.toList()

        assertEquals(listOf(localUsers, localUsers + remoteUsers), result)
    }

    @Test
    fun emitsEmptyList_whenNoLocalOrRemoteDataIsAvailable() = runTest {
        coEvery { localUserDataSource?.getUsers() } returns emptyList()
        coEvery { remoteUserDataSource?.fetchUsers() } returns flow { emit(emptyList()) }

        val result = userRepository?.getUsers()?.toList()

        assertEquals(listOf(emptyList<User>()), result)
    }

    @Test
    fun emitsUser_whenUserIsFoundById() = runTest {
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
        coEvery { localUserDataSource?.getUserById(1) } returns user

        val result = userRepository?.getUserById(1)?.toList()

        assertEquals(listOf(user), result)
    }

    @Test
    fun emitsNull_whenUserIsNotFoundById() = runTest {
        coEvery { localUserDataSource?.getUserById(99) } returns null

        val result = userRepository?.getUserById(99)?.toList()

        assertEquals(listOf(null), result)
    }
}