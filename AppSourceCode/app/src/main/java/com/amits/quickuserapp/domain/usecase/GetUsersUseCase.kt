package com.amits.quickuserapp.domain.usecase

import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching a list of users.
 * This class encapsulates the logic for retrieving user data from the repository.
 *
 * @property repository The repository instance used to fetch user data.
 */
class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    /**
     * Invokes the use case to fetch a list of users.
     *
     * @return A Flow emitting lists of User objects.
     */
    suspend operator fun invoke(): Flow<List<User>> = repository.getUsers()
}