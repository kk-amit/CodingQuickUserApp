package com.amits.quickuserapp.presentation

import com.amits.quickuserapp.domain.model.User

/**
 * Represents the different UI states for the user-related screens.
 * This sealed class is used to model the state of the UI, such as loading, success, or error.
 */
sealed class UserUiState {

    /**
     * Represents the loading state of the UI.
     * This state is used when data is being fetched or processed.
     */
    data object Loading : UserUiState()

    /**
     * Represents the success state of the UI.
     * This state contains a list of users that were successfully fetched.
     *
     * @property users The list of users retrieved.
     */
    data class Success(val users: List<User>) : UserUiState()

    /**
     * Represents the error state of the UI.
     * This state contains an error message describing the issue.
     *
     * @property message The error message to display.
     */
    data class Error(val message: String) : UserUiState()
}