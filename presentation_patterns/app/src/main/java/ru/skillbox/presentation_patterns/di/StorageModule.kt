package ru.skillbox.presentation_patterns.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.skillbox.presentation_patterns.data.storage.Pref

@InstallIn(SingletonComponent::class)
@Module
object StorageModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): Pref {
        return Pref(context)
    }
}