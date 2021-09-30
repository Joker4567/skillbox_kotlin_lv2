package ru.skillbox.presentation_patterns.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.skillbox.presentation_patterns.data.network.api.WeatherApi
import ru.skillbox.presentation_patterns.data.network.repository.WeatherRepository
import ru.skillbox.presentation_patterns.data.network.repository.WeatherRepositoryImpl
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideWeatherRepository(
            errorHandler: ErrorHandler,
            weatherDao: WeatherDao,
            weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(
                errorHandler = errorHandler,
                api = weatherApi,
                weatherDao = weatherDao
        )
    }
}