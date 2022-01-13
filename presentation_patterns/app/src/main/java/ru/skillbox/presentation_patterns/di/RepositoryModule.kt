package ru.skillbox.presentation_patterns.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.skillbox.presentation_patterns.data.network.api.WeatherApi
import ru.skillbox.presentation_patterns.data.network.repository.*
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherNetworkRepository(
            weatherDao: WeatherDao,
            weatherApi: WeatherApi
    ): WeatherNetworkRepository {
        return WeatherNetworkRepositoryImpl(
                api = weatherApi,
                weatherDao = weatherDao
        )
    }

    @Provides
    fun provideWeatherLocalRepository(
            weatherDao: WeatherDao
    ) : WeatherDaoRepository {
        return WeatherDaoRepositoryImpl(
                weatherDao
        )
    }
}