package com.amits.quickuserapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data model class representing a user entity in the database.
 * This class is annotated with Room's @Entity to define a table in the database.
 *
 * @property id The unique identifier for the user. This is the primary key.
 * @property name The name of the user.
 * @property company The company associated with the user.
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property address The address of the user.
 * @property zip The zip code of the user's address.
 * @property state The state of the user's address.
 * @property country The country of the user's address.
 * @property phone The phone number of the user.
 * @property photo The photo URL or path of the user.
 */
@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val company: String,
    val username: String,
    val email: String,
    val address: String,
    val zip: String,
    val state: String,
    val country: String,
    val phone: String,
    val photo: String
)