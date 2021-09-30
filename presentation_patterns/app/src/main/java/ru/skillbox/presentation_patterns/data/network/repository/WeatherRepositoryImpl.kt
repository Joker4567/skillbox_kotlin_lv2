package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.data.network.api.WeatherApi
import ru.skillbox.presentation_patterns.data.network.model.WeatherScheme
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.utils.extension.getDateCurrent
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler
import ru.skillbox.presentation_patterns.utils.platform.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        errorHandler: ErrorHandler,
        private val api: WeatherApi,
        private val weatherDao: WeatherDao
) : BaseRepository(errorHandler = errorHandler), WeatherRepository {

    override suspend fun getWeatherCity(
            city: String,
            onSuccess: (List<WeatherEntity>) -> Unit,
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
                weatherDao.getWeatherCity(city)
            }
            else
                emptyList()
        }
    }

    override suspend fun getCityLocal(
            city: String
    ) : List<WeatherEntity> =
            weatherDao.getWeatherCity(city)

    override suspend fun deleteArrayCity(
            city: String
    ) {
        weatherDao.getWeatherCity(city).forEach {
            weatherDao.deleteGameAppOfId(it.id)
        }
    }

    override suspend fun getCityListSaveLocal(): List<String> =
            weatherDao.getCityLocal()
}