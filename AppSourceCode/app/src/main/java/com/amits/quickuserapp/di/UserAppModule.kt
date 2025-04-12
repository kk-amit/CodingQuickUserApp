package com.amits.quickuserapp.di

import android.content.Context
import androidx.room.Room
import com.amits.quickuserapp.BuildConfig
import com.amits.quickuserapp.data.api.UserApiService
import com.amits.quickuserapp.data.db.UserDao
import com.amits.quickuserapp.data.db.UserDatabase
import com.amits.quickuserapp.data.repository.UserRepositoryImpl
import com.amits.quickuserapp.data.repository.datasource.LocalUserDataSource
import com.amits.quickuserapp.data.repository.datasource.RemoteUserDataSource
import com.amits.quickuserapp.domain.repository.UserRepository
import com.amits.quickuserapp.util.BASE_URL
import com.amits.quickuserapp.util.DB_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, DB_FILE_NAME).build()

    @Provides
    fun provideDao(db: UserDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideRepository(
        remoteUserDataSource: RemoteUserDataSource,
        localUserDataSource: LocalUserDataSource
    ): UserRepository =
        UserRepositoryImpl(
            remoteUserDataSource = remoteUserDataSource,
            localUserDataSource = localUserDataSource
        )

    //@IODispatcher
    @Provides
    @Named("IO")
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}