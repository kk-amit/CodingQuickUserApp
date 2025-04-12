package com.amits.quickuserapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amits.quickuserapp.domain.model.User
import com.amits.quickuserapp.domain.usecase.GetUsersUseCase
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

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
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

    fun loadUsers() {
        viewModelScope.launch(ioDispatcher) {
            getUsersUseCase()
                .catch {
                    // Updates the state to an error state if an exception occurs.
                    _state.value = UserUiState.Error(it.message ?: "Error")
                }
                .collect { users ->
                    // Updates the state to success if users are fetched successfully.
                    if (users.isEmpty()) {
                        _state.value = UserUiState.Error("Empty User List")
                        return@collect
                    }
                    _state.value = UserUiState.Success(users)
                }
        }
    }
}