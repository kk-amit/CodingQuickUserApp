package com.amits.quickuserapp.presentation

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amits.quickuserapp.R
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.usecase.GetUserByIdUseCase
import com.amits.quickuserapp.domain.usecase.GetUsersUseCase
import com.amits.quickuserapp.util.strEmptyUserList
import com.amits.quickuserapp.util.strError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * ViewModel class for managing user-related UI state and business logic.
 * This class interacts with use cases to fetch user data and exposes state to the UI.
 *
 * @property getUsersUseCase Use case for fetching a list of users.
 * @property getUserByIdUseCase Use case for fetching a user by their ID.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // Mutable state flow to hold the current UI state of the user list.
    private val _state = MutableStateFlow<UserUiState>(UserUiState.Loading)

    /**
     * Publicly exposed state flow for observing the UI state of the user list.
     */
    val state: StateFlow<UserUiState> = _state.asStateFlow()

    // Mutable state flow to hold the currently selected user.
    private val _user = MutableStateFlow<User?>(null)

    /**
     * Publicly exposed state flow for observing the currently selected user.
     */
    val user: StateFlow<User?> = _user.asStateFlow()

    /**
     * Loads the list of users by invoking the `getUsersUseCase`.
     * Updates the UI state based on the result of the operation.
     * Emits a loading state initially, followed by success or error states.
     */
    fun loadUsers() {
        viewModelScope.launch(ioDispatcher) {
            getUsersUseCase()
                .catch {
                    // Updates the state to an error state if an exception occurs.
                    _state.value = UserUiState.Error(it.message ?: strError)
                }
                .collect { users ->
                    // Updates the state to success if users are fetched successfully.
                    if (users.isEmpty()) {
                        _state.value = UserUiState.Error(strEmptyUserList)
                        return@collect
                    }
                    _state.value = UserUiState.Success(users)
                }
        }
    }

    /**
     * Fetches a user by their ID by invoking the `getUserByIdUseCase`.
     * Updates the `_user` state flow with the fetched user.
     *
     * @param userId The unique identifier of the user to fetch.
     */
    fun getUser(userId: Int) {
        viewModelScope.launch(ioDispatcher) {
            getUserByIdUseCase(userId).collect {
                _user.value = it
            }
        }
    }

    /**
     * Sets the current UI state to a loading state.
     * This method is used to indicate that a loading operation is in progress.
     */
    fun setLoadingState() {
        _state.value = UserUiState.Loading
    }
}