package com.amits.quickuserapp.data.repository.datasource

import android.util.Log
import com.amits.quickuserapp.data.api.UserApiService
import com.amits.quickuserapp.data.model.UserEntity
import com.amits.quickuserapp.util.API_DELAY_TIME
import com.amits.quickuserapp.util.API_RETRY_COUNT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

/**
 * Data source for managing remote user data.
 * This class provides methods to fetch user data from a remote API.
 *
 * @property api The UserApiService instance used to perform API calls.
 */
class RemoteUserDataSource @Inject constructor(
    private val api: UserApiService
) {

    /**
     * Fetches a list of users from the remote API.
     * The flow retries the API call a specified number of times in case of failure
     * and emits an empty list if an error occurs after all retries.
     *
     * @return A Flow emitting a list of UserEntity objects retrieved from the API.
     */
    fun fetchUsers(): Flow<List<UserEntity>> = flow {
        // Emit the list of users fetched from the API
        emit(api.getUsers())
    }.retryWhen { _, attempt ->
        // Retry the API call if the attempt count is less than the retry limit
        if (attempt < API_RETRY_COUNT) {
            delay(API_DELAY_TIME) // Delay before retrying
            true
        } else false
    }.catch { e ->
        // Log the error and emit an empty list in case of failure
        Log.e("RemoteUserDataSource", "Error: ${e.message}")
        emit(emptyList())
    }
}