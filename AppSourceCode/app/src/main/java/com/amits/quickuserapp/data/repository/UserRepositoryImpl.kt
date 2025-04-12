package com.amits.quickuserapp.data.repository

import com.amits.quickuserapp.data.mapper.toDomain
import com.amits.quickuserapp.data.repository.datasource.RemoteUserDataSource
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource
) : UserRepository {

    override suspend fun getUsers(): Flow<List<User>> = flow {

        // Try fetching remote users with retry logic
        remoteUserDataSource.fetchUsers().collect { remoteUsers ->
            if (remoteUsers.isNotEmpty()) {
                emit(remoteUsers.map { it.toDomain() })
            }
        }
    }
}