package com.amits.quickuserapp.data.repository.datasource

import android.util.Log
import com.amits.quickuserapp.data.api.UserApiService
import com.amits.quickuserapp.data.model.UserRemote
import com.amits.quickuserapp.util.API_DELAY_TIME
import com.amits.quickuserapp.util.API_RETRY_COUNT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val api: UserApiService
) {

    fun fetchUsers(): Flow<List<UserRemote>> = flow {
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