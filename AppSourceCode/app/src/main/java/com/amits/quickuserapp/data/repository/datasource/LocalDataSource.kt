package com.amits.quickuserapp.data.repository.datasource

import com.amits.quickuserapp.data.db.UserDao
import com.amits.quickuserapp.data.mapper.toDomain
import com.amits.quickuserapp.data.model.UserEntity
import com.amits.quickuserapp.domain.model.User
import javax.inject.Inject

/**
 * Data source for managing local user data.
 * This class provides methods to interact with the local database through the UserDao.
 *
 * @property dao The UserDao instance used to perform database operations.
 */
class LocalUserDataSource @Inject constructor(private val dao: UserDao) {

    /**
     * Retrieves all users from the local database.
     *
     * @return A list of User objects mapped from the database entities.
     */
    suspend fun getUsers(): List<User> = dao.getAll().map { it.toDomain() }

    /**
     * Retrieves a user by their unique identifier from the local database.
     *
     * @param id The unique identifier of the user to fetch.
     * @return A User object mapped from the database entity, or null if not found.
     */
    suspend fun getUserById(id: Int): User? = dao.getUserById(id)?.toDomain()

    /**
     * Saves a list of users to the local database.
     *
     * @param users The list of UserEntity objects to be inserted into the database.
     */
    suspend fun saveUsers(users: List<UserEntity>) = dao.insertAll(users)
}