package com.amits.quickuserapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amits.quickuserapp.data.model.UserEntity

/**
 * Data Access Object (DAO) for performing database operations on the `UserEntity` table.
 * This interface provides methods to interact with the user data stored in the local database.
 */
@Dao
interface UserDao {

    /**
     * Retrieves all user records from the `UserEntity` table.
     *
     * @return A list of `UserEntity` objects representing all users in the database.
     * This method is a suspend function and must be called within a coroutine or another suspend function.
     */
    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    /**
     * Retrieves a specific user record from the `UserEntity` table by its ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A `UserEntity` object representing the user with the specified ID, or `null` if no such user exists.
     * This method is a suspend function and must be called within a coroutine or another suspend function.
     */
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    /**
     * Inserts a list of user records into the `UserEntity` table.
     * If a conflict occurs (e.g., a record with the same ID already exists), the existing record will be replaced.
     *
     * @param users A list of `UserEntity` objects to insert into the database.
     * This method is a suspend function and must be called within a coroutine or another suspend function.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)
}