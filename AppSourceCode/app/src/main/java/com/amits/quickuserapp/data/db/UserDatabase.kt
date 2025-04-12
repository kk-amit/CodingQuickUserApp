package com.amits.quickuserapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amits.quickuserapp.data.model.UserEntity

/**
 * The Room database class for the application.
 * This class serves as the main access point to the persisted data.
 *
 * @property userDao Provides access to the DAO for performing database operations on the `UserEntity` table.
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    /**
     * Abstract method to retrieve the `UserDao` instance.
     *
     * @return An instance of `UserDao` for performing CRUD operations on the `UserEntity` table.
     */
    abstract fun userDao(): UserDao
}