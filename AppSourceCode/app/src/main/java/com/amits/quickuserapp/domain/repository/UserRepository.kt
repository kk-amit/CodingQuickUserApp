package com.amits.quickuserapp.domain.repository

import com.amits.quickuserapp.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user data.
 * This interface defines methods for fetching user data from various sources.
 */
interface UserRepository {

    /**
     * Fetches a list of users as a Flow.
     * The implementation of this method should provide a stream of user data.
     *
     * @return A Flow emitting lists of User objects.
     */
    suspend fun getUsers(): Flow<List<User>>

    /**
     * Fetches a user by their unique identifier as a Flow.
     * The implementation of this method should provide a stream containing the user data or null if not found.
     *
     * @param id The unique identifier of the user to fetch.
     * @return A Flow emitting the User object or null if the user is not found.
     */
    suspend fun getUserById(id: Int): Flow<User?>
}