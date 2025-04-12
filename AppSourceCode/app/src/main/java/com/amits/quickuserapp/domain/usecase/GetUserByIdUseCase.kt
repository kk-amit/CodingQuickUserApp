package com.amits.quickuserapp.domain.usecase

import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching a user by their unique identifier.
 * This class encapsulates the logic for retrieving a specific user's data from the repository.
 *
 * @property repository The repository instance used to fetch user data.
 */
class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    /**
     * Invokes the use case to fetch a user by their ID.
     *
     * @param id The unique identifier of the user to fetch.
     * @return A Flow emitting the User object or null if the user is not found.
     */
    suspend operator fun invoke(id: Int): Flow<User?> = repository.getUserById(id)
}