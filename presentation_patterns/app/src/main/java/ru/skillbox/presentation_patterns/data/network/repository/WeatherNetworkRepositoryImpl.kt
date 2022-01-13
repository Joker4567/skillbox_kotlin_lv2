package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.data.network.api.WeatherApi
import ru.skillbox.presentation_patterns.data.network.model.WeatherScheme
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.domain.mapper.mapToWeatherUI
import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.extension.getDateCurrent
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler
import ru.skillbox.presentation_patterns.utils.platform.BaseRepository
import ru.skillbox.presentation_patterns.utils.platform.State
import javax.inject.Inject

class WeatherNetworkRepositoryImpl @Inject constructor(
        errorHandler: ErrorHandler,
        private val api: WeatherApi,
        private val weatherDao: WeatherDao
) : BaseRepository(errorHandler = errorHandler), WeatherNetworkRepository {

    override suspend fun getWeatherCity(
            city: String,
            onSuccess: (List<WeatherUI>) -> Unit,
            onState: (State) -> Unit
    ) {
        execute(onSuccess = onSuccess, onState = onState) {
            val responseResult = api.getCityWeather(city).execute()
            if(responseResult.isSuccessful) {
                val weatherScheme = responseResult.body() as WeatherScheme
                weatherDao.insertWeather(
                        WeatherEntity(
                                id = 0,
                                temperature = weatherScheme.temperature,
                                description = weatherScheme.description,
                                city,
                                getDateCurrent()
                        )
                )
                weatherDao.getWeatherCity(city).map { it.mapToWeatherUI() }
            }
            else
                emptyList()
        }
    }
}