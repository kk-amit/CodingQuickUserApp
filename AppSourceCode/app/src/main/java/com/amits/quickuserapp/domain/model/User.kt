package com.amits.quickuserapp.domain.model

/**
 * Data class representing a user in the domain layer.
 * This class is used to model user-related data within the application.
 *
 * @property id The unique identifier for the user.
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
data class User(
    val id: Int,
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
