package ru.skillbox.presentation_patterns.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.skillbox.presentation_patterns.data.room.AppDatabase
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideSkillBoxDB(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.buildDataSource(context)
    }

    @Provides
    fun provideGameCardDao(enazaDatabase: AppDatabase): WeatherDao {
        return enazaDatabase.getWeatherDao()
    }
}