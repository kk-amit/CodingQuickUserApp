package com.amits.quickuserapp.data.repository

import com.amits.quickuserapp.data.mapper.toDomain
import com.amits.quickuserapp.data.repository.datasource.LocalUserDataSource
import com.amits.quickuserapp.data.repository.datasource.RemoteUserDataSource
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of the UserRepository interface.
 * This class provides methods to fetch user data from both local and remote sources.
 *
 * @property remoteUserDataSource The data source for fetching remote user data.
 * @property localUserDataSource The data source for managing local user data.
 */
class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {

    /**
     * Fetches a list of users as a Flow.
     * The method first emits local users (if available) and then attempts to fetch remote users.
     * Remote data fetching includes retry logic and updates the local database upon success.
     *
     * @return A Flow emitting lists of User objects.
     */
    override suspend fun getUsers(): Flow<List<User>> = flow {

        // Check local data first and emit it to the UI
        val localUsers = localUserDataSource.getUsers()
        if (localUsers.isNotEmpty()) emit(localUsers)

        // Try fetching remote users with retry logic
        remoteUserDataSource.fetchUsers().collect { remoteUsers ->
            if (remoteUsers.isNotEmpty()) {
                localUserDataSource.saveUsers(remoteUsers) // Save remote users to local database
            }
            emit(localUserDataSource.getUsers()) // Emit updated local data
        }
    }
}