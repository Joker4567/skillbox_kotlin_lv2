package ru.skillbox.presentation_patterns.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return ErrorHandler(Gson())
    }
}