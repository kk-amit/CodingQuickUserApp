package com.amits.quickuserapp.di

import com.amits.quickuserapp.BuildConfig
import com.amits.quickuserapp.data.api.UserApiService
import com.amits.quickuserapp.data.repository.UserRepositoryImpl
import com.amits.quickuserapp.data.repository.datasource.RemoteUserDataSource
import com.amits.quickuserapp.domain.repository.UserRepository
import com.amits.quickuserapp.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserAppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideApi(baseUrl: String): UserApiService {

        // HttpLoggingInterceptor for logging network requests and responses
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteUserDataSource: RemoteUserDataSource,
    ): UserRepository =
        UserRepositoryImpl(
            remoteUserDataSource = remoteUserDataSource,
        )

    //@IODispatcher
    @Provides
    @Named("IO")
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}