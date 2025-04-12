package com.amits.quickuserapp.data.api

import com.amits.quickuserapp.data.model.UserRemote
import retrofit2.http.GET

/**
 * Interface defining the API service for user-related operations.
 * This service uses Retrofit to make HTTP requests to the backend.
 */
interface UserApiService {

    /**
     * Fetches a list of users from the backend.
     *
     * @return A list of `UserEntity` objects representing the users retrieved from the API.
     * This method is a suspend function, meaning it must be called within a coroutine or another suspend function.
     */
    @GET("users")
    suspend fun getUsers(): List<UserRemote>
}